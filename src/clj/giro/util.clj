(ns giro.util)

(defn select-keys-inv
  "Returns a map containing only those entries in map whose key is NOT in keys. The opposite of clojure.core/select-keys."
  [map keyseq]
  (select-keys map (filter (complement #(contains?
                                          (set keyseq) %))
                           (keys map)))

  )

(defn print-and-return
  "Diagnostic tool for printing the result of evaluating an s-expression.
   Any initial args over 1, are printed as strings, and generally used as a label."
  [& xs]
  (when (seq (butlast xs))
    (println (apply str (butlast xs))))
  (last xs))

