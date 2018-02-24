(ns giro.user-agent-test
  (:use midje.sweet)
  (:require [giro.user-agent :refer :all]))

(fact "matches? returns true/false on success/failed match"
      (matches? #".*" "A") => true
      (matches? #"A" "A") => true
      (matches? #"A" "B") => false)


(fact "'matches' returns a single correct assertion"
      (matches  [[#"something" :platform ..irrelevant..]
                         [#"otherthing" :platform ..irrelevant..]
                         [#"foobar" :platform ..match..]]
                "foobar") => '({:platform ..match..})
      )

(fact "'matches' returns successive assertions"
      (matches [[#"nope" ..irrelevent.. ..irrelevent..]
                         [#"^foo.*" ..match1.. ..match1..]
                         [#".*bar$" ..match2.. ..match2..]]
               "foobar")
      => '({..match1.. ..match1..} {..match2.. ..match2..}))


(tabular
  (fact "'detect-platform' works with a real example"
        (detect-platform ?ua) => ?platform)
  ?platform ?ua
  :amazon "Mozilla/5.0 (Linux; Android 4.4.3; KFTHWI Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Silk/47.1.79 like Chrome/47.0.2526.80 Safari/537.36"
  :amazon "Mozilla/5.0 (Linux; U; Android 2.3.4; en-us; Silk/1.0.22.79_10013310) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 Silk-Accelerated=true"
  :android "Mozilla/5.0 (Linux; Android 7.0; Pixel C Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/52.0.2743.98 Safari/537.36"
  :android "Mozilla/5.0 (Linux; U; Android 4.0.3; en-gb; GT-I9100 Build/IML74K) AppleWebKit/534.30 (KHTML"
  :ios "Mozilla/5.0 (iPad; U; CPU OS 3_2_1 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML"
  :ios "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_2 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) CriOS/60.0.3112.72 Mobile/14F89 Safari/602.1"
  :ios "Mozilla/5.0 (iPhone U CPU iPhone OS 4_3_5 like Mac OS X en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8L1 Safari/6533.18.5"
  :ios "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8F190 Safari/6533.18.5"
  :ios "Opera/9.80 (iPhone; Opera Mini/6.13548/25.872; U; en) Presto/2.5.25 Version/10.54"
  :macosx "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/601.4.4 (KHTML, like Gecko)"
  :macosx "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"
  :macosx "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/603.3.8 (KHTML, like Gecko) Version/10.1.2 Safari/603.3.8"
  :macosx "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:54.0) Gecko/20100101 Firefox/54.0"
  :windowsphone "Mozilla/5.0 (Windows Phone 10.0; Android 4.2.1; Microsoft; Lumia 950) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Mobile Safari/537.36 Edge/13.10586"
  :win "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"
  :win "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0"
  :win "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36"
  :unknown "nope"
  )
