(ns api.routes
  (:require [api.interceptors.json :as ji]
            [io.pedestal.http.route :as route]
            [api.diplomat.http-in.authentication :as http.in.authentication]
            [api.diplomat.http-in.customer :as http.in.customer]
            [api.interceptors.auth :as ai]
            [io.pedestal.http.body-params :as body-params]))


(def common-interceptors [ji/transform-body ji/content-interceptor (body-params/body-params)])

(def private-routes-interceptors
  (conj common-interceptors ai/auth-interceptor))

(def routes
  (route/expand-routes
    #{
      ["/customer" :post (conj private-routes-interceptors http.in.customer/create-customer) :route-name :customer-create]
      ["/customer" :get (conj private-routes-interceptors http.in.customer/find-all) :route-name :customer-find-all]
      ["/customer/:id" :get (conj private-routes-interceptors http.in.customer/find-by-id) :route-name :customer-find-by-id]
      ["/customer/disable" :post (conj private-routes-interceptors http.in.customer/disable-customer) :route-name :customer-disable]
      ["/authentication" :post (conj common-interceptors http.in.authentication/post-authentication) :route-name :authorization]
      }))