import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class LinearTest extends AnyFlatSpec with ChiselScalatestTester {
  "Linear4x2" should "compute y = x * W^T + bias" in {
    test(new Linear4x2) { c =>
      c.io.x(0).poke(1.S)
      c.io.x(1).poke(2.S)
      c.io.x(2).poke(3.S)
      c.io.x(3).poke(4.S)

      c.io.w(0)(0).poke(1.S)
      c.io.w(0)(1).poke(1.S)
      c.io.w(0)(2).poke(1.S)
      c.io.w(0)(3).poke(1.S)

      c.io.w(1)(0).poke(1.S)
      c.io.w(1)(1).poke(2.S)
      c.io.w(1)(2).poke(3.S)
      c.io.w(1)(3).poke(4.S)

      c.io.bias(0).poke(10.S)
      c.io.bias(1).poke(0.S)

      c.io.y(0).expect(20.S)
      c.io.y(1).expect(30.S)
    }
  }
}