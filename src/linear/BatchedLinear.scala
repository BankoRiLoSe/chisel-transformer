import chisel3._

class BatchedLinear(
    seqLen: Int,
    inFeatures: Int,
    outFeatures: Int,
    dataWidth: Int = 16,
    accWidth: Int = 32,
    outputWidth: Int = 16,
    shift: Int = 0
) extends Module {
  val io = IO(new Bundle {
    val x = Input(Vec(seqLen, Vec(inFeatures, SInt(dataWidth.W))))
    val w = Input(Vec(outFeatures, Vec(inFeatures, SInt(dataWidth.W))))
    val bias = Input(Vec(outFeatures, SInt(accWidth.W)))
    val y = Output(Vec(seqLen, Vec(outFeatures, SInt(outputWidth.W))))
  })

  for (t <- 0 until seqLen) {
    val linear = Module(
      new Linear(
        inFeatures,
        outFeatures,
        dataWidth,
        accWidth,
        outputWidth,
        shift
      )
    )

    linear.io.x := io.x(t)
    linear.io.w := io.w
    linear.io.bias := io.bias
    io.y(t) := linear.io.y
  }
}
