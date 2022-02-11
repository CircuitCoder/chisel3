// SPDX-License-Identifier: Apache-2.0

package chisel3.experimental.util.algorithm

import chisel3._

/** Map each bits to logical or of itself and all bits less siginificant than it.
  * @example {{{
  * LSBOr("b00001000".U) // Returns "b11111000".U
  * LSBOr("b00010100".U) // Returns "b11111100".U
  * LSBOr("b00000000".U) // Returns "b00000000".U
  * }}}
  * This circuit seems to be high fan out, but synthesis tool should handle this.
  */
object LSBOr {
  def apply(data: UInt): UInt = {
    val width = data.widthOption match {
      case Some(w) => w
      case None    => throw new IllegalArgumentException("Cannot call LSBOr on data with unknown width.")
    }
    VecInit(Seq.tabulate(width) { i: Int =>
      VecInit(data.asBools().dropRight(width - i - 1)).asUInt().orR()
    }).asUInt()
  }
}

/** Map each bits to logical or of itself and all bits more siginificant than it.
  * @example {{{
  * MSBOr("b00001000".U) // Returns "b00001111".U
  * MSBOr("b00010100".U) // Returns "b00011111".U
  * MSBOr("b00000000".U) // Returns "b00000000".U
  * }}}
  * This circuit seems to be high fan out, but synthesis tool should handle this.
  */
object MSBOr {
  def apply(data: UInt): UInt = {
    val width = data.widthOption match {
      case Some(w) => w
      case None    => throw new IllegalArgumentException("Cannot call MSBOr on data with unknown width.")
    }
    VecInit(Seq.tabulate(width) { i: Int =>
      VecInit(data.asBools().drop(i)).asUInt().orR()
    }).asUInt()
  }
}
