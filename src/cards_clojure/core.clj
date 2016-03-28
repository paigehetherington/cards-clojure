(ns cards-clojure.core
  (:gen-class))

(def suits [:clubs :spades :hearts :diamonds])
(def ranks (range 1 14)) ; first inclusive/last exclusive so will be 1-13
(def rank-names {1 :ace, 11 :jack, 12 :queen, 13 :king})
(def test-flush #{{:rank 1 :suit :clubs}
                  {:rank 2 :suit :clubs}
                  {:rank 3 :suit :clubs}
                  {:rank 4 :suit :clubs}})

(def test-straight-flush #{{:rank 3 :suit :clubs}
                           {:rank 4 :suit :clubs}
                           {:rank 5 :suit :clubs}
                           {:rank 6 :suit :clubs}})

(def test-straight #{{:rank 3 :suit :hearts}
                     {:rank 4 :suit :clubs}
                     {:rank 5 :suit :diamonds}
                     {:rank 6 :suit :clubs}})

(def test-four #{{:rank 3 :suit :hearts}
                 {:rank 3 :suit :clubs}
                 {:rank 3 :suit :diamonds}
                 {:rank 3 :suit :spades}})

(def test-two #{{:rank 2 :suit :hearts}
                {:rank 4 :suit :clubs}
                {:rank 2 :suit :diamonds}
                {:rank 4 :suit :spades}})

(def test-three #{{:rank 5 :suit :hearts}
                  {:rank 4 :suit :clubs}
                  {:rank 4 :suit :spades}
                  {:rank 4 :suit :diamonds}})

(defn create-deck []
  (set ;returns list and turns into set
    (for [suit suits
          rank ranks]
      {:suit suit
       :rank rank}))) ; 3rd argument to get as default

(defn create-hands [deck]
  (set
    (for [c1 deck
          c2 (disj deck c1)
          c3 (disj deck c1 c2)
          c4 (disj deck c1 c2 c3)]
      #{c1 c2 c3 c4}))) ; HS 

(defn flush? [hand]
  (= 1 (count (set (map :suit hand)))))

(defn straight-flush? [hand]
  (let [[c1 c2 c3 c4] (vec (sort (map :rank hand)))]
    (and (= 1 (count (set (map :suit hand))))
         (= c4 (inc c3))
         (= c3 (inc c2))
         (= c2 (inc c1)))))
         
  
(defn straight? [hand]
  (let [[c1 c2 c3 c4] (vec (sort (map :rank hand)))]
    (and (> (count (set (map :suit hand))) 1)
         (= c4 (inc c3))
         (= c3 (inc c2))
         (= c2 (inc c1)))))

(defn four-of-a-kind? [hand]
  (= 1 (count (set (map :rank hand)))))

(defn two-pair? [hand]
  (let [[c1 c2 c3 c4] (vec (sort (map :rank hand)))]
    (and (= c1 c2)
         (= c3 c4)))) 
      
      ;(= vec (get 0) vec (get 1))
         ;(= vec (get 2) vec (get 3)))))

(defn three-of-a-kind? [hand]
  (let [[c1 c2 c3 c4] (vec (sort (map :rank hand)))]
     (= c1 c2 c3)))

(defn -main []
  (time ;benchmark
    (let [deck (create-deck)
          hands (create-hands deck)
          flush-hands (filter flush? hands)]
      (count flush-hands))))
            
