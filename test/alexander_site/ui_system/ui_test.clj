(ns alexander-site.ui-system.ui-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [alexander-site.ui-system.ui :as sut]))

(deftest css-class-str-test
  (testing "With a qualified keyword, returns a matching CSS class string."
    (is (= "simple-ns__class-sel"
           (sut/css-class-str :simple-ns/class-sel)))
    (is (= "domain_project_my-feature__class-sel"
           (sut/css-class-str :domain.project.my-feature/class-sel))))
  (testing "With an unqualified keyword,"
    (testing (str "that IS a Garden class selector keyword, returns a matching "
                  "class string.")
      (is (= "class-sel"
             (sut/css-class-str :.class-sel))))
    (testing "that IS NOT a Garden class selector keyword, throws an error."
      (is (thrown? AssertionError
                   (sut/css-class-str :unqualified-non-class-sel-kw))))))

(deftest garden-sel-kw-test
  (testing (str "With a qualified keyword, returns a matching Garden class "
                "selector keyword.")
    (is (= :.simple-ns__class-sel
           (sut/garden-sel-kw :simple-ns/class-sel)))
    (is (= :.domain_project_my-feature__class-sel
           (sut/garden-sel-kw :domain.project.my-feature/class-sel))))
  (testing "With an unqualified keyword, throws an error."
    (is (thrown? AssertionError (sut/garden-sel-kw :type-sel)))
    (is (thrown? AssertionError (sut/garden-sel-kw :.class-sel)))
    (is (thrown? AssertionError (sut/garden-sel-kw :#id-sel)))))

(deftest transform-qualified-sel-kws-for-garden-css-compilation-test
  (testing "With styles including"
    (testing (str "only vectors, returns styles with qualified keywords"
                  "transformed.")
      (is (= [[:type-sel {:a :b}]
              [:.class-sel {:c :d}]
              [:.domain_project_my-feature__class-sel {:e :f}]]
             (sut/transform-qualified-sel-kws-for-garden-css-compilation
              [[:type-sel {:a :b}]
               [:.class-sel {:c :d}]
               [:domain.project.my-feature/class-sel {:e :f}]]))))
    (testing (str "mixed elements, returns styles with qualified keywords"
                  "transformed.")
      (is (= [{:a :b}
              [:.domain_project_my-feature__class-sel {:c :d}]
              [:#id-sel {:e :f}]]
             (sut/transform-qualified-sel-kws-for-garden-css-compilation
              [{:a :b}
               [:domain.project.my-feature/class-sel {:c :d}]
               [:#id-sel {:e :f}]]))))))

(class (sut/transform-qualified-sel-kws-for-garden-css-compilation [[:a :b]]))

(deftest html-test
  (testing (str "With a hiccup form representing an empty/void HTML element, "
                "returns HTML without a trailing slash (HTML, not XHTML)")
    (is (= "<meta charset=\"utf-8\">"
           (str (sut/html [:meta {:charset "utf-8"}]))))
    (is (= "<br>"
           (str (sut/html [:br])))))
  (testing "With multiple unnested hiccup forms, returns HTML for all"
    (is (= "<h1>a</h1><h2>b</h2><h3>c</h3>"
           (str (sut/html [:h1 "a"]
                          [:h2 "b"]
                          [:h3 "c"])))))
  (testing (str "With a hiccup form containing a string with a <script> tag, "
                "returns HTML with the string escaped, preventing cross-site "
                "scripting attacks")
    (is (= "<p>&lt;script&gt;alert(&apos;hi&apos;)&lt;/script&gt;</p>"
           (str (sut/html [:p "<script>alert('hi')</script>"])))))
  (testing (str "With a hiccup form containing a
                `dangerously-unescapable-string` with a <script> tag, returns "
                "HTML with the <script> tag unescaped, allowing cross-site "
                "scripting attacks")
    (is (= "<p><script>alert('hi')</script></p>"
           (str (sut/html [:p (sut/dangerously-unescapable-string
                               "<script>alert('hi')</script>")]))))))

(deftest dangerously-unescapable-string-test
  (testing (str "With a string containing a <script> tag, returns an object "
                "whose `.toString` method returns the unescaped string")
    (is (= "<script>"
           (.toString (sut/dangerously-unescapable-string "<script>"))))
    (is (= "<script></script>"
           (str (sut/dangerously-unescapable-string "<script></script>"))))))

(deftest cs-test
  (testing "With one valid class, returns a string containing one CSS class."
    (is (= "class-sel-string" (sut/cs "class-sel-string")))
    (is (= "class-sel-keyword" (sut/cs :.class-sel-keyword)))
    (is (= "domain_project_my-feature__class-sel"
           (sut/cs :domain.project.my-feature/class-sel))))
  (testing "With multiple valid classes,"
    (testing "returns a string containing all as CSS classes."
      (is (= "class-sel-kw my-ns__class-sel string"
             (sut/cs :.class-sel-kw :my-ns/class-sel "string"))))
    (testing "and one invalid class, throws an error."
      (is (thrown? AssertionError (sut/cs :.ok :INVALID :my-ns/ok "ok"))))))
