/*
 * Copyright 2014 Paul Horn
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
package scalax.transducers
package internal

import scala.language.higherKinds
import scala.reflect.ClassTag

private[transducers] final class CombinedTransducer[A, B, C](left: Transducer[A, B], right: Transducer[B, C]) extends Transducer[A, C] {
  def apply[R](rf: Reducer[C, R]) = left(right(rf))
  override def toString = s"$left.$right"
}

private[transducers] final class FilterTransducer[A](f: A ⇒ Boolean) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new FilterReducer[A, R](rf, f)
  override def toString = "(filter)"
}

private[transducers] final class FilterNotTransducer[A](f: A ⇒ Boolean) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new FilterNotReducer[A, R](rf, f)
  override def toString = "(filterNot)"
}

private[transducers] final class MapTransducer[A, B](f: A ⇒ B) extends Transducer[A, B] {
  def apply[R](rf: Reducer[B, R]) =
    new MapReducer[B, A, R](rf, f)
  override def toString = "(map)"
}

private[transducers] final class CollectTransducer[A, B](pf: PartialFunction[A, B]) extends Transducer[A, B] {
  def apply[R](rf: Reducer[B, R]) =
    new CollectReducer[A, B, R](rf, pf)
  override def toString = "(collect)"
}

private[transducers] final class ForeachTransducer[A](f: A ⇒ Unit) extends Transducer[A, Unit] {
  def apply[R](rf: Reducer[Unit, R]) =
    new ForeachReducer[A, R](rf, f)
  override def toString = "(foreach)"
}

private[transducers] final class FlatMapTransducer[A, B, F[_]: AsSource](f: A ⇒ F[B]) extends Transducer[A, B] {
  def apply[R](rf: Reducer[B, R]) =
    new FlatMapReducer[A, B, R, F](rf, f)
  override def toString = "(flatMap)"
}

private[transducers] final class TakeTransducer[A](n: Long) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new TakeReducer[A, R](rf, n)
  override def toString = s"(take $n)"
}

private[transducers] final class TakeWhileTransducer[A](f: A ⇒ Boolean) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new TakeWhileReducer[A, R](rf, f)
  override def toString = "(takeWhile)"
}

private[transducers] final class TakeRightTransducer[A: ClassTag](n: Int) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new TakeRightReducer[A, R](rf, n)
  override def toString = s"(takeRight $n)"
}

private[transducers] final class TakeNthTransducer[A](n: Long) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new TakeNthReducer[A, R](rf, n)
  override def toString = s"(takeNth $n)"
}

private[transducers] final class DropTransducer[A](n: Long) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new DropReducer[A, R](rf, n)
  override def toString = s"(drop $n)"
}

private[transducers] final class DropWhileTransducer[A](f: A ⇒ Boolean) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new DropWhileReducer[A, R](rf, f)
  override def toString = "(dropWhile)"
}

private[transducers] final class DropRightTransducer[A: ClassTag](n: Int) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new DropRightReducer[A, R](rf, n)
  override def toString = s"(dropRight $n)"
}

private[transducers] final class DropNthTransducer[A](n: Long) extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new DropNthReducer[A, R](rf, n)
  override def toString = s"(dropNth $n)"
}

private[transducers] final class DistinctTransducer[A] extends Transducer[A, A] {
  def apply[R](rf: Reducer[A, R]) =
    new DistinctReducer[A, R](rf)
  override def toString = "(distinct)"
}

private[transducers] final class ZipWithIndexTransducer[A] extends Transducer[A, (A, Int)] {
  def apply[R](rf: Reducer[(A, Int), R]) =
    new ZipWithIndexReducer[A, R](rf)
  override def toString = "(zipWithIndex)"
}

private[transducers] final class GroupedTransducer[A, F[_]](n: Int)(implicit F: AsTarget[F], S: Sized[F]) extends Transducer[A, F[A]] {
  def apply[R](rf: Reducer[F[A], R]) =
    new GroupedReducer[A, R, F](rf, n)
  override def toString = s"(grouped $n)"
}

private[transducers] final class PartitionTransducer[A, B <: AnyRef, F[_]](f: A ⇒ B)(implicit F: AsTarget[F], S: Sized[F]) extends Transducer[A, F[A]] {
  def apply[R](rf: Reducer[F[A], R]) =
    new PartitionReducer[A, B, R, F](rf, f)
  override def toString = "(partition)"
}