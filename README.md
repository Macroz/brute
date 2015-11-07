# brute

brute lets you to *plot it simple stupid*.

## Usage

```clj
(:use [brute.core])
```

```clj
(spit "example/20.svg" (plot {:fill "black"} (repeatedly 200 #(- (rand) 0.5))
                             {:fill "green" :opacity "0.5"} (repeatedly 20 #(- (rand) 0.5))))
```

![20.svg](https://rawgit.com/Macroz/brute/master/example/20.svg)

```clj
(spit "example/800.svg" (plot {} (repeatedly 800 #(- (rand) 0.5))))
```

![800.svg](https://rawgit.com/Macroz/brute/master/example/800.svg))

```clj
(spit "example/signal.svg"
      (plot {} (take 1280 signal)
            {:fill "#393" :opacity 0.5} (average 1024 (take 1280 signal))))
```

![signal.svg](https://rawgit.com/Macroz/brute/master/example/signal.svg))


## License

Copyright © 2015 Markku Rontu

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
