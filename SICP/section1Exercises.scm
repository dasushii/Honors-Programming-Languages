(define (main)
    (println "Author: Eric Wang ericwang@westminster.net")
    )
; Exercise 1.2
(/ (+ 5 4 (- 2 (- 3 (+ 6 (/ 4 5))))) (* 3 (- 6 2) (- 2 7)))
; Because integers are rounded down, this expression should evaluate to 0, which is exactly what the terminal returns

; Exercise 1.3
(define (square a) (* a a))
(define (sumOfSquares x y) (+ (square x) (square y)))
(define (coolMethod i j k)
    (cond ((and (< i j) (< i k)) (sumOfSquares y z))
        ((and (< j i) (< j k)) (sumOfSquares x z))
        (else (sumOfSquares x y))))
; coolMethod is the procedure.

; Exercise 1.4
(define (a-plus-abs-b a b)
  ((if (> b 0) + -) a b))
; Here, if b is greater than 0, it runs a+b. If b is less than 0, it runs a-b. So, this procedure computes a+|b|