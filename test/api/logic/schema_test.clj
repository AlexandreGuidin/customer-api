(ns api.logic.schema-test
  (:require
    [clojure.test :refer :all]
    [api.logic.schema :as logic.s]
    [api.models.customer :as customer]
    [schema.core :as s])
  (:import (clojure.lang ExceptionInfo)))


(def valid-customer {:id        "65ae7286-9879-43f5-b2cc-2d5f65f660da"
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

(def invalid-customer-error {:errors [{:id "is-not-a-valid-uuid"} {:birthDate "not-in-format-2011-12-03"}]})


(defn validate-and-extract
  [schema data]
  (try
    (s/validate schema data)
    (catch ExceptionInfo ex (logic.s/extract-error-body ex))))

(deftest validate-schema-test
  (testing "Testing extracting full body error"

    (is (= "abc" (validate-and-extract s/Str "abc")))
    (is (= {:errors ["instance? java.lang.String"]} (validate-and-extract s/Str 123)))

    (is (= 123 (validate-and-extract s/Num 123)))
    (is (= {:errors ["instance? java.lang.Number"]} (validate-and-extract s/Num "123")))

    (is (= :new (validate-and-extract customer/Status :new)))
    (is (= {:errors ["wrong-status-value"]} (validate-and-extract customer/Status :xxx)))

    (is (= {:errors ["not-in-format-2011-12-03"]} (validate-and-extract customer/LocalDateSchema "20111203")))
    (is (= {:errors ["not-in-format-2011-12-03"]} (validate-and-extract customer/LocalDateSchema "")))
    (is (= {:errors ["not-in-format-2011-12-03"]} (validate-and-extract customer/LocalDateSchema nil)))

    (is (= {:errors ["not-in-format-2011-12-03T10:15:30+01:00"]} (validate-and-extract customer/ZonedDateTimeSchema "20111203T10:15:30+01:00")))
    (is (= {:errors ["not-in-format-2011-12-03T10:15:30+01:00"]} (validate-and-extract customer/ZonedDateTimeSchema "")))
    (is (= {:errors ["not-in-format-2011-12-03T10:15:30+01:00"]} (validate-and-extract customer/ZonedDateTimeSchema nil)))

    (is (= valid-customer (validate-and-extract customer/CustomerEntity valid-customer)))
    (is (= invalid-customer-error (validate-and-extract customer/CustomerEntity invalid-customer)))
    )
  )
