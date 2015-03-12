/*
 * Copyright 2014 – 2015 Paul Horn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package scalax.transducers.internal

import java.util.concurrent.atomic.AtomicBoolean

class Reduced {
  private[this] final val state = new AtomicBoolean()

  def apply[T](x: T): T = {
    if (!state.compareAndSet(false, true)) {
      throw new IllegalStateException("ContractViolation: Reduced state signaled multiple times, check for bugs in code.")
    }
    x
  }

  override def toString = s"Reduced(${state.get()})"

  override def hashCode() = 31 * state.get().##

  override def equals(obj: scala.Any): Boolean = obj match {
    case r: Reduced ⇒ r.? == state.get()
    case _          ⇒ false
  }

  def ? : Boolean = state.get()
}
