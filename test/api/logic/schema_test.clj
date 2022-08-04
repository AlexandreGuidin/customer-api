(ns api.logic.schema-test
  (:require
    [clojure.test :as t :refer :all]
    [api.logic.schema :as logic.s]
    [api.models.customer :as customer]
    [schema.core :as s]))


(def valid-customer {:id        #uuid "65ae7286-9879-43f5-b2cc-2d5f65f660da"
                     :name      "name"
                     :lastName  "last name"
                     :status    :new
                     :birthDate "2011-12-03"
                     :createdAt "2011-12-03T10:15:30+01:00"})

(def invalid-customer {:id        "foo"
                       :name      "name"
                       :lastName  "last name"
                       :status    :new
                       :birthDate "20111203"
                       :createdAt "2011-12-03T10:15:30+01:00"})

(def invalid-customer-error '({:id "instance? java.util.UUID"} {:birthDate "not-in-format-2011-12-03"}))

(defmethod t/assert-expr 'thrown-with-data? [msg form]
  (let [data (second form)
        body (nthnext form 2)]
    `(try ~@body
          (do-report {:type     :fail, :message ~msg,
                      :expected '~form, :actual nil})
          (catch clojure.lang.ExceptionInfo e#
            (let [expected# ~data
                  actual# (ex-data e#)]
              (if (= expected# actual#)
                (do-report {:type     :pass, :message ~msg,
                            :expected expected#, :actual actual#})
                (do-report {:type     :fail, :message ~msg,
                            :expected expected#, :actual actual#})))
            e#))))

(deftest validate-schema-test
  (testing "Testing validation method"

    (is (= "abc" (logic.s/validate-schema s/Str "abc")))
    (is (thrown-with-data? {:errors "instance? java.lang.String" :type "validation-error"} (logic.s/validate-schema s/Str 123)))

    (is (= 123 (logic.s/validate-schema s/Num 123)))
    (is (thrown-with-data? {:errors "instance? java.lang.Number" :type "validation-error"} (logic.s/validate-schema s/Num "123")))

    (is (= :new (logic.s/validate-schema customer/Status :new)))
    (is (thrown-with-data? {:errors "wrong-status-value" :type "validation-error"} (logic.s/validate-schema customer/Status :xxx)))


    (is (thrown-with-data? {:errors "not-in-format-2011-12-03" :type "validation-error"} (logic.s/validate-schema customer/LocalDateSchema "20111203")))
    (is (thrown-with-data? {:errors "not-in-format-2011-12-03" :type "validation-error"} (logic.s/validate-schema customer/LocalDateSchema "")))
    (is (thrown-with-data? {:errors "not-in-format-2011-12-03" :type "validation-error"} (logic.s/validate-schema customer/LocalDateSchema nil)))


    (is (thrown-with-data? {:errors "not-in-format-2011-12-03T10:15:30+01:00" :type "validation-error"} (logic.s/validate-schema customer/ZonedDateTimeSchema "20111203T10:15:30+01:00")))
    (is (thrown-with-data? {:errors "not-in-format-2011-12-03T10:15:30+01:00" :type "validation-error"} (logic.s/validate-schema customer/ZonedDateTimeSchema "")))
    (is (thrown-with-data? {:errors "not-in-format-2011-12-03T10:15:30+01:00" :type "validation-error"} (logic.s/validate-schema customer/ZonedDateTimeSchema nil)))


    (is (= valid-customer (logic.s/validate-schema customer/CustomerEntity valid-customer)))
    (is (thrown-with-data? {:errors invalid-customer-error :type "validation-error"} (logic.s/validate-schema customer/CustomerEntity invalid-customer)))
    )
  )
