// domain specific language - jezyk dziedzinowy 

implicit class Pipe[A](value: A) {
    //
    def |>[B](f: A => B): B = f(value)
}

def ><[B, C, D](f: (b: B, c: C) => D) = (c: C) => (b: B) => f(b, c) 

object ListConv: 
    //
    def mapTuples(list: List[ (Int, Int) ], whichOne: Int) = 
        list.map( element => element.productElement(whichOne)  )
end ListConv

val a = List( (1,2), (3,4) ) 
a |> ( >< (ListConv.mapTuples) )(1) 


def >^^<[B, C, D](f: (b: B, c: C) => D) = (c: C) => (b: B) => f(b, c) 
def >^^^<[B, C, D, E](f: (b: B, c: C, d: D) => E) = (d: D) => (b: B) => (c: C)  => f(b, c, d) 

// class MySpec extends FlatSpec with Matchers {
//   "A Stack" should "pop values in last-in-first-out order" in {
//     val stack = new scala.collection.mutable.Stack[Int]
//     stack.push(1)
//     stack.push(2)
//     stack.pop() should be (2)
//     stack.pop() should be (1)
//   }
// }

// import fs2.Stream 
// import cats.effect._

// val s1 = Stream.iterate(1)(_ + 1) 
// s1.take(10).toList 
// s1.drop(5).toList 
