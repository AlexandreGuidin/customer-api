(ns api.logic.authentication-test
  (:require [clojure.test :refer :all]
            [api.logic.authentication :refer :all]
            [clojure.string :as str])
  (:import (clojure.lang ExceptionInfo)))


(deftest decode-test
  (testing "Decode a base64 string"
    (is (= "admin:admin-pwd\n" (decode (.getBytes "YWRtaW46YWRtaW4tcHdkCg=="))))
    (is (= "admin:adminwrong\n" (decode (.getBytes "YWRtaW46YWRtaW53cm9uZwo="))))
    (is (not (str/blank? (decode (.getBytes "xxxxxx")))))
    (is (= "" (decode (.getBytes "xx"))))
    (is (= "" (decode (.getBytes ""))))
    (is (thrown? ExceptionInfo (decode (.getBytes "xx=")))))

  (testing "Test decoding a base64 header"
    (is (= "admin:admin-pwd" (decode-auth-header "YWRtaW46YWRtaW4tcHdkCg==")))
    (is (= "admin:adminwrong" (decode-auth-header "YWRtaW46YWRtaW53cm9uZwo=")))
    (is (thrown-with-msg? ExceptionInfo #"Given token was not a basic auth header" (decode-auth-header "xxxxxx"))))

  (testing "Extract user and pwd from decoded header"
    (is (= ["admin" "admin-pwd"] (extract-user-and-pwd "admin:admin-pwd")))
    (is (= ["admin" "adminwrong"] (extract-user-and-pwd "admin:adminwrong")))
    (is (= [""] (extract-user-and-pwd ""))))

  (testing "Check if given a credentials, it is authenticated"
    (is (is-authenticated? ["admin" "admin-pwd"] expected-users))
    (is (is-authenticated? ["user" "user-pwd"] expected-users))
    (is (not (is-authenticated? ["user" "user-wrong-pwd"] expected-users)))
    (is (not (is-authenticated? ["user-random" "user-pwd"] expected-users))))

  (testing "Generating JWT token"
    (is (not (empty? (generate-jwt-token)))))

  (testing "Generate jwt from basic auth header"
    (is (not (empty? (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :token))))
    (is (= 900 (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :exp)))
    (is (thrown-with-msg? ExceptionInfo #"Given user was not authorized" (basic-auth "YWRtaW46YWRtaW53cm9uZwo="))))
  )

(deftest decode-header-test
  (testing "Test decoding a base64 header"
    (is (= "admin:admin-pwd" (decode-auth-header "YWRtaW46YWRtaW4tcHdkCg==")))
    (is (= "admin:adminwrong" (decode-auth-header "YWRtaW46YWRtaW53cm9uZwo=")))
    (is (thrown-with-msg? ExceptionInfo #"Given token was not a basic auth header" (decode-auth-header "xxxxxx"))))

  (testing "Extract user and pwd from decoded header"
    (is (= ["admin" "admin-pwd"] (extract-user-and-pwd "admin:admin-pwd")))
    (is (= ["admin" "adminwrong"] (extract-user-and-pwd "admin:adminwrong")))
    (is (= [""] (extract-user-and-pwd ""))))

  (testing "Check if given a credentials, it is authenticated"
    (is (is-authenticated? ["admin" "admin-pwd"] expected-users))
    (is (is-authenticated? ["user" "user-pwd"] expected-users))
    (is (not (is-authenticated? ["user" "user-wrong-pwd"] expected-users)))
    (is (not (is-authenticated? ["user-random" "user-pwd"] expected-users))))

  (testing "Generating JWT token"
    (is (not (empty? (generate-jwt-token)))))

  (testing "Generate jwt from basic auth header"
    (is (not (empty? (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :token))))
    (is (= 900 (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :exp)))
    (is (thrown-with-msg? ExceptionInfo #"Given user was not authorized" (basic-auth "YWRtaW46YWRtaW53cm9uZwo="))))
  )

(deftest extract-user-pwd-test

  (testing "Extract user and pwd from decoded header"
    (is (= ["admin" "admin-pwd"] (extract-user-and-pwd "admin:admin-pwd")))
    (is (= ["admin" "adminwrong"] (extract-user-and-pwd "admin:adminwrong")))
    (is (= [""] (extract-user-and-pwd ""))))

  (testing "Check if given a credentials, it is authenticated"
    (is (is-authenticated? ["admin" "admin-pwd"] expected-users))
    (is (is-authenticated? ["user" "user-pwd"] expected-users))
    (is (not (is-authenticated? ["user" "user-wrong-pwd"] expected-users)))
    (is (not (is-authenticated? ["user-random" "user-pwd"] expected-users))))

  (testing "Generating JWT token"
    (is (not (empty? (generate-jwt-token)))))

  (testing "Generate jwt from basic auth header"
    (is (not (empty? (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :token))))
    (is (= 900 (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :exp)))
    (is (thrown-with-msg? ExceptionInfo #"Given user was not authorized" (basic-auth "YWRtaW46YWRtaW53cm9uZwo="))))
  )

(deftest is-authenticated-test

  (testing "Check if given a credentials, it is authenticated"
    (is (is-authenticated? ["admin" "admin-pwd"] expected-users))
    (is (is-authenticated? ["user" "user-pwd"] expected-users))
    (is (not (is-authenticated? ["user" "user-wrong-pwd"] expected-users)))
    (is (not (is-authenticated? ["user-random" "user-pwd"] expected-users))))

  (testing "Generating JWT token"
    (is (not (empty? (generate-jwt-token)))))

  (testing "Generate jwt from basic auth header"
    (is (not (empty? (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :token))))
    (is (= 900 (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :exp)))
    (is (thrown-with-msg? ExceptionInfo #"Given user was not authorized" (basic-auth "YWRtaW46YWRtaW53cm9uZwo="))))
  )

(deftest generate-jwt-test
    (testing "Generating JWT token"
    (is (not (empty? (generate-jwt-token)))))

  (testing "Generate jwt from basic auth header"
    (is (not (empty? (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :token))))
    (is (= 900 (get (basic-auth "YWRtaW46YWRtaW4tcHdkCg==") :exp)))
    (is (thrown-with-msg? ExceptionInfo #"Given user was not authorized" (basic-auth "YWRtaW46YWRtaW53cm9uZwo="))))
  )