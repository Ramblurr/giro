(ns giro.util-test
  (:use midje.sweet)
  (:require [giro.util :refer :all]))

(fact "inverse of select-keys"
      (select-keys-inv {:1 1 :2 2} [:1]) => {:2 2})

(fact "print-and-returns returns its last value"
      (print-and-return "Hello world" "debug message" ..something..) => ..something..)

