import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class BatchedLinearTest extends AnyFlatSpec with ChiselScalatestTester {
  "BatchedLinear" should "apply the same linear layer to each token" in {
    test(new BatchedLinear(2, 3, 2, 8, 32, 16, 0)) { c =>
      c.io.x(0)(0).poke(1.S)
      c.io.x(0)(1).poke(2.S)
      c.io.x(0)(2).poke(3.S)

      c.io.x(1)(0).poke(4.S)
      c.io.x(1)(1).poke(5.S)
      c.io.x(1)(2).poke(6.S)

      c.io.w(0)(0).poke(1.S)
      c.io.w(0)(1).poke(1.S)
      c.io.w(0)(2).poke(1.S)

      c.io.w(1)(0).poke(1.S)
      c.io.w(1)(1).poke(2.S)
      c.io.w(1)(2).poke(3.S)

      c.io.bias(0).poke(0.S)
      c.io.bias(1).poke(0.S)

      c.io.y(0)(0).expect(6.S)
      c.io.y(0)(1).expect(14.S)

      c.io.y(1)(0).expect(15.S)
      c.io.y(1)(1).expect(32.S)
    }
  }
}
