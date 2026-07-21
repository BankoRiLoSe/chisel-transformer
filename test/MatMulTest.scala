import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class MatMulTest extends AnyFlatSpec with ChiselScalatestTester {
  "MatMul" should "compute matrix multiplication" in {
    test(new MatMul(2, 3, 2, 8, 32)) { c =>
      // A = [[1, 2, 3],
      //      [4, 5, 6]]
      c.io.a(0)(0).poke(1.S)
      c.io.a(0)(1).poke(2.S)
      c.io.a(0)(2).poke(3.S)

      c.io.a(1)(0).poke(4.S)
      c.io.a(1)(1).poke(5.S)
      c.io.a(1)(2).poke(6.S)

      // B = [[1, 2],
      //      [3, 4],
      //      [5, 6]]
      c.io.b(0)(0).poke(1.S)
      c.io.b(0)(1).poke(2.S)

      c.io.b(1)(0).poke(3.S)
      c.io.b(1)(1).poke(4.S)

      c.io.b(2)(0).poke(5.S)
      c.io.b(2)(1).poke(6.S)

      // Y = A @ B
      // Y[0][0] = 1*1 + 2*3 + 3*5 = 22
      // Y[0][1] = 1*2 + 2*4 + 3*6 = 28
      // Y[1][0] = 4*1 + 5*3 + 6*5 = 49
      // Y[1][1] = 4*2 + 5*4 + 6*6 = 64
      c.io.y(0)(0).expect(22.S)
      c.io.y(0)(1).expect(28.S)
      c.io.y(1)(0).expect(49.S)
      c.io.y(1)(1).expect(64.S)
    }
  }
}
