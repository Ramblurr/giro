(ns giro.test.handler
  (:use midje.sweet)
  (:require [ring.mock.request :refer :all]
            [giro.handler :refer :all]))

(facts "Testing giro"
       (fact "Getting the root page should return a non-error"
             (let [response ((app) (request :get "/"))]
               (:status response) => 200))

       (fact "Getting a giro that doesn't exist should return a 404"
             (let [response ((app) (request :get "/does-not-exist"))]
               (:status response) => 404)))
