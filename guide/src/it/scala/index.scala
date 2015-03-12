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

import scalax.transducers.internal.TransducersSpec

import org.specs2.Specification
import org.specs2.specification.{Snippets, Forms}

object index extends Specification with Snippets with Forms { def is = "Transducers for Scala".title ^ s2"""

### Installation

${ "Installation" ~/ install }

### Usage Guide

${ "Usage Guide" ~/ guide }
$p
${ "Detailed Spec" ~ TransducersSpec }

"""
}
