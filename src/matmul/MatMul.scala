import chisel3._

class MatMul(
    m: Int,
    k: Int,
    n: Int,
    dataWidth: Int = 16,
    accWidth: Int = 32
) extends Module {
  val io = IO(new Bundle {
    val a = Input(Vec(m, Vec(k, SInt(dataWidth.W))))
    val b = Input(Vec(k, Vec(n, SInt(dataWidth.W))))
    val y = Output(Vec(m, Vec(n, SInt(accWidth.W))))
  })

  for (i <- 0 until m) {
    for (j <- 0 until n) {
      val products = Wire(Vec(k, SInt(accWidth.W)))

      for (t <- 0 until k) {
        products(t) := io.a(i)(t) * io.b(t)(j)
      }

      io.y(i)(j) := products.reduce(_ + _)
    }
  }
}
