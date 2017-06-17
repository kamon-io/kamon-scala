/*
 * =========================================================================================
 * Copyright © 2013-2017 the kamon project <http://kamon.io/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 * =========================================================================================
 */

package kamon.instrumentation.mixin

import io.opentracing.ActiveSpan.Continuation
import kamon.Kamon
import kamon.agent.api.instrumentation.Initializer
import kamon.util.HasContinuation

/**
  * Mixin for scala.concurrent.impl.CallbackRunnable
  * Mixin for scala.concurrent.impl.Future$PromiseCompletingRunnable
  */
class HasContinuationMixin extends HasContinuation {
  var continuation:Continuation = _

  @Initializer
  def init(): Unit = this.continuation = Kamon.activeSpanContinuation()
}