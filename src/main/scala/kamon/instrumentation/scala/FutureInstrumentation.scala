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

package kamon.instrumentation.scala

import kamon.agent.scala.KamonInstrumentation
import kamon.instrumentation.scala.advisor.RunMethodAdvisor
import kamon.instrumentation.mixin.HasContinuationMixin
import kamon.instrumentation.scalaz.advisor.{ParameterSubstitutionAdvisor => ScalazParameterSubstitutionAdvisor}
import kamon.instrumentation.twitter.advisor.{ParameterSubstitutionAdvisor => TwitterParameterSubstitutionAdvisor}

class FutureInstrumentation extends KamonInstrumentation {

  /**
    * Instrument:
    *
    * scala.concurrent.impl.CallbackRunnable::run
    * scala.concurrent.impl.Future$PromiseCompletingRunnable::run
    *
    * Mix:
    *
    * scala.concurrent.impl.CallbackRunnable with kamon.instrumentation.mixin.HasContinuationMixin
    * scala.concurrent.impl.Future$PromiseCompletingRunnable kamon.instrumentation.mixin.HasContinuationMixin
    *
    */
  forTargetType("scala.concurrent.impl.CallbackRunnable" or "scala.concurrent.impl.Future$PromiseCompletingRunnable") { builder ⇒
    builder
      .withMixin(classOf[HasContinuationMixin])
      .withAdvisorFor(named("run"), classOf[RunMethodAdvisor])
      .build()
  }

  /**
    * Instrument:
    *
    * scalaz.concurrent.Future::apply
    *
    */
  forTargetType("scalaz.concurrent.Future$") { builder ⇒
    builder
      .withAdvisorFor(named("apply"), classOf[ScalazParameterSubstitutionAdvisor])
      .build()
  }

  /**
    * Instrument:
    *
    * com.twitter.util.FuturePool::apply
    */
  forTargetType("com.twitter.util.FuturePool$") { builder ⇒
    builder
      .withAdvisorFor(named("apply"), classOf[TwitterParameterSubstitutionAdvisor])
      .build()
  }
}
