import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class RequantizeTest extends AnyFlatSpec with ChiselScalatestTester {
  "Requantize" should "right shift values" in {
    test(new Requantize(32, 16, 2)) { c =>
      c.io.in.poke(200.S)
      c.io.out.expect(50.S)
    }
  }

  it should "saturate positive overflow" in {
    test(new Requantize(32, 8, 0)) { c =>
      c.io.in.poke(1000.S)
      c.io.out.expect(127.S)
    }
  }

  it should "saturate negative overflow" in {
    test(new Requantize(32, 8, 0)) { c =>
      c.io.in.poke((-1000).S)
      c.io.out.expect((-128).S)
    }
  }
}
