(ns api.logic.schema-test
  (:require
    [clojure.test :refer :all]
    [api.logic.schema :as logic.s]
    [api.models.customer :as customer]
    [schema.core :as s]))


(deftest validate-schema-test



  (testing "Testing validation method"

    (def valid-customer {:id        "65ae7286-9879-43f5-b2cc-2d5f65f660da"
                         :name      "name"
                         :lastName  "last name"
                         :status    :new
                         :birthDate "2011-12-03"
                         :createdAt "2011-12-03T10:15:30+01:00"}
      )

    (def invalid-customer {:id        "foo"
                           :name      "name"
                           :lastName  "last name"
                           :status    :new
                           :birthDate "20111203"
                           :createdAt "2011-12-03T10:15:30+01:00"})

    (def invalid-customer-error '({:id "instance? java.util.UUID"} {:birthDate "format should be '2011-12-03'"}))

    (is (= "abc" (logic.s/validate-schema s/Str "abc")))
    (is (= :new (logic.s/validate-schema customer/Status :new)))
    (is (= "instance? java.lang.String" (logic.s/validate-schema s/Str 123)))
    (is (= "format should be '2011-12-03'" (logic.s/validate-schema customer/LocalDateSchema "20111203")))

    (is (= valid-customer (logic.s/validate-schema customer/CustomerEntity valid-customer)))
    (is (= invalid-customer-error (logic.s/validate-schema customer/CustomerEntity invalid-customer)))
    )
  )
