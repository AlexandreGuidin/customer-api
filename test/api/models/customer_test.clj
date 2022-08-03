(ns api.models.customer-test
  (:require
    [clojure.test :refer :all]
    [api.models.customer :as customer]
    [schema.core :as s :include-macros true])
  (:import (clojure.lang ExceptionInfo)))

(deftest is-valid-localdate-test
  (testing "Testing localdate validator"
    (is (customer/is-valid-localdate? "2011-12-03"))
    (is (not (customer/is-valid-localdate? "20111203")))
    (is (not (customer/is-valid-localdate? "2011-02-30")))
    (is (not (customer/is-valid-localdate? "")))
    (is (not (customer/is-valid-localdate? nil))))
  )

(deftest is-valid-zoned-datetime-test
  (testing "Testing zoneddatetime validator"
    (is (customer/is-valid-zoned-datetime? "2011-12-03T10:15:30+01:00"))
    (is (customer/is-valid-zoned-datetime? "2011-12-03T10:15:30+00:00"))
    (is (not (customer/is-valid-zoned-datetime? "2011-12-03T10:15:30")))
    (is (not (customer/is-valid-zoned-datetime? "2011-12-03T101530+01:00")))
    (is (not (customer/is-valid-zoned-datetime? "20111203T10:15:30+01:00")))
    (is (not (customer/is-valid-zoned-datetime? "2011-02-30")))
    (is (not (customer/is-valid-zoned-datetime? "")))
    (is (not (customer/is-valid-zoned-datetime? nil))))
  )


(deftest is-valid-localdate-schema
  (testing "Testing localdate schema"
    (is (not (nil? (s/validate customer/LocalDateSchema "2011-12-03"))))
    (is (thrown? ExceptionInfo (s/validate customer/LocalDateSchema "20111203")))
    )
  )

(deftest is-valid-zoneddatetime-schema
  (testing "Testing localdate schema"
    (is (not (nil? (s/validate customer/ZonedDateTimeSchema "2011-12-03T10:15:30+01:00"))))
    (is (thrown? ExceptionInfo (s/validate customer/ZonedDateTimeSchema "20111203T10:15:30+01:00")))
    )
  )