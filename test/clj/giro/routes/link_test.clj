(ns giro.routes.link-test
  (:use midje.sweet)
  (:require [giro.routes.link :refer :all]))

(fact "platform->giro-key-test"
      (platform->giro-key :amazon) => :url_amazon)

(fact "platform->giro-key-test non-existent"
      (platform->giro-key :blackberry) => nil)


(facts "resolving platforms to giro urls"
       (def test-giro {
                       :url_amazon   ..amazon..
                       :url_fallback ..fallback..
                       })

       (fact "platform->girl-url resolves to existing value"
             (platform->giro-url :amazon test-giro) => ..amazon..)

       (fact "platform->girl-url resolves fallback for non existing platforms"
             (platform->giro-url :apple test-giro) => ..fallback..)

       (fact "platform->girl-url resolves fallback for unknown platforms"
             (platform->giro-url :unknown test-giro) => ..fallback..)

       (fact "platform->girl-url resolves fallback for unknown platforms"
             (platform->giro-url nil test-giro) => ..fallback..)
       )

