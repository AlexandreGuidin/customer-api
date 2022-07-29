(ns api.database.db-test
  (:require
    [clojure.test :refer :all]
    [clj-time.core :as time]
    [api.database.db :as db]
    )
  )


(def mock-user {:id (java.util.UUID/randomUUID) :name "test" :lastName "test-lastname" :status "new" :birthDate "01/01/2000" :createdAt (time/local-date-time 2010 1 30 0 0 0 0)})
(defn other-user
  []
  (update mock-user :id (fn [_] (java.util.UUID/randomUUID))))

(deftest db-test
  (testing "Test find all empty"
    (is (= [] (db/find-all))))

  (testing "Test find all with one user"
    (db/create-new mock-user)
    (is (= [mock-user] (db/find-all))))

  (testing "Test find all with multiple user"
    (let [new-user (other-user)]
      (db/create-new new-user)
      (is (= [mock-user, new-user] (db/find-all)))))

  (testing "Test delete all"
    (db/create-new mock-user)
    (db/create-new other-user)
    (db/create-new other-user)
    (is (= [] (db/delete-all))))

  (testing "Test find by id"
    (db/create-new mock-user)
    (is (= [mock-user] (db/find-by-id (:id mock-user))))
    (is (= [] (db/find-by-id "123")))
    (nil? (db/find-by-id "123")))
  )
