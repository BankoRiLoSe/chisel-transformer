import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class LinearTest extends AnyFlatSpec with ChiselScalatestTester {
  "Linear" should "compute y = x * W^T + bias" in {
    test(new Linear(4, 2)) { c =>
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
  it should "support different dimensions" in {
    test(new Linear(3, 1)) { c =>
      c.io.x(0).poke(2.S)
      c.io.x(1).poke(3.S)
      c.io.x(2).poke(4.S)

      c.io.w(0)(0).poke(1.S)
      c.io.w(0)(1).poke(2.S)
      c.io.w(0)(2).poke(3.S)

      c.io.bias(0).poke(5.S)

      c.io.y(0).expect(25.S)
    }
  }
  it should "support custom data and accumulator widths" in {
    test(new Linear(3, 1, 8, 32)) { c =>
      c.io.x(0).poke(2.S)
      c.io.x(1).poke(3.S)
      c.io.x(2).poke(4.S)

      c.io.w(0)(0).poke(1.S)
      c.io.w(0)(1).poke(2.S)
      c.io.w(0)(2).poke(3.S)

      c.io.bias(0).poke(5.S)

      c.io.y(0).expect(25.S)
    }
  }

  it should "handle signed values" in {
    test(new Linear(2, 1, 8, 32)) { c =>
      c.io.x(0).poke((-2).S)
      c.io.x(1).poke(3.S)

      c.io.w(0)(0).poke(4.S)
      c.io.w(0)(1).poke((-5).S)

      c.io.bias(0).poke(1.S)

      c.io.y(0).expect((-22).S)
    }
  }

  it should "requantize accumulated outputs" in {
    test(new Linear(3, 1, 8, 32, 16, 2)) { c =>
      c.io.x(0).poke(10.S)
      c.io.x(1).poke(20.S)
      c.io.x(2).poke(30.S)

      c.io.w(0)(0).poke(2.S)
      c.io.w(0)(1).poke(3.S)
      c.io.w(0)(2).poke(4.S)

      c.io.bias(0).poke(0.S)

      c.io.y(0).expect(50.S)
    }
  }
  it should "saturate requantized outputs" in {
    test(new Linear(3, 1, 8, 32, 8, 0)) { c =>
      c.io.x(0).poke(100.S)
      c.io.x(1).poke(100.S)
      c.io.x(2).poke(100.S)

      c.io.w(0)(0).poke(2.S)
      c.io.w(0)(1).poke(2.S)
      c.io.w(0)(2).poke(2.S)

      c.io.bias(0).poke(0.S)

      c.io.y(0).expect(127.S)
    }
  }
}
