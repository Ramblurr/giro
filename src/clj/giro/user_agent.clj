(ns giro.user-agent)

(def UA-PATTERNS
  [[#".*\bSilk\b.*" :platform :amazon]
   [#".*i(?:Phone|Pad|Pod).*" :platform :ios]
   [#".*Windows Phone.*" :platform :windowsphone]
   [#".*Android.*" :platform :android]
   [#".*Macintosh.*" :platform :macosx]
   [#".*\bWindows NT\b.*" :platform :win]
   ])

(defn matches? [pattern input]
  (not (clojure.string/blank? (re-matches pattern input))))

(defn matches
  "Returns a list of assertions that are probably true about the input string."
  [patterns ua]
  (remove nil? (for [[matcher & {:as assertions}] patterns]
                 (when-let [result (matches? matcher ua)]
                   assertions))))

(defn detect-platform
  "Returns a keyword of the most likely device platform one of
     :amazon, :android , :ios, :macosx, :windowsphone, :win"
  [ua]
  (if-some [platform (:platform (first (matches UA-PATTERNS ua)))]
    platform
    :unknown))
