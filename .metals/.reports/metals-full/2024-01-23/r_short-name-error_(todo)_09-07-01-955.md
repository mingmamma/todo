error id: Function
### java.lang.ClassCastException: class scala.meta.internal.pc.printer.ShortenedNames$PrettyType cannot be cast to class dotty.tools.dotc.core.Types$TypeRef (scala.meta.internal.pc.printer.ShortenedNames$PrettyType and dotty.tools.dotc.core.Types$TypeRef are in unnamed module of loader java.net.URLClassLoader @4d5f7069)

Error while printing type, could not create short name for type: 
AppliedType(TypeRef(ThisType(TypeRef(NoPrefix,module class function)),trait Function),List(TypeBounds(TypeRef(ThisType(TypeRef(NoPrefix,module class data)),class Id),TypeRef(ThisType(TypeRef(NoPrefix,module class <special-ops>)),type <FromJavaObject>)), TypeBounds(TypeRef(ThisType(TypeRef(NoPrefix,module class scala)),class Nothing),TypeParamRef(U))))


#### Error stacktrace:

```
scala.meta.internal.pc.printer.ShortenedNames.loop$1(ShortenedNames.scala:167)
	scala.meta.internal.pc.printer.ShortenedNames.processOwners$1(ShortenedNames.scala:133)
	scala.meta.internal.pc.printer.ShortenedNames.shortened$lzyINIT1$1(ShortenedNames.scala:147)
	scala.meta.internal.pc.printer.ShortenedNames.shortened$1(ShortenedNames.scala:147)
	scala.meta.internal.pc.printer.ShortenedNames.loop$1(ShortenedNames.scala:154)
	scala.meta.internal.pc.printer.ShortenedNames.loop$1(ShortenedNames.scala:189)
	scala.meta.internal.pc.printer.ShortenedNames.shortType(ShortenedNames.scala:217)
	scala.meta.internal.pc.printer.MetalsPrinter.$anonfun$1(MetalsPrinter.scala:73)
	scala.util.Try$.apply(Try.scala:210)
	scala.meta.internal.pc.printer.MetalsPrinter.tpe(MetalsPrinter.scala:73)
	scala.meta.internal.pc.printer.MetalsPrinter.paramLabel(MetalsPrinter.scala:373)
	scala.meta.internal.pc.printer.MetalsPrinter.$anonfun$5(MetalsPrinter.scala:206)
	scala.collection.immutable.List.flatMap(List.scala:293)
	scala.meta.internal.pc.printer.MetalsPrinter.label$1$$anonfun$1(MetalsPrinter.scala:209)
	scala.collection.immutable.List.flatMap(List.scala:293)
	scala.meta.internal.pc.printer.MetalsPrinter.label$1(MetalsPrinter.scala:214)
	scala.meta.internal.pc.printer.MetalsPrinter.defaultMethodSignature(MetalsPrinter.scala:217)
	scala.meta.internal.pc.completions.OverrideCompletions$.toCompletionValue(OverrideCompletions.scala:430)
	scala.meta.internal.pc.completions.OverrideCompletions$.contribute$$anonfun$1(OverrideCompletions.scala:127)
	scala.collection.immutable.List.map(List.scala:250)
	scala.meta.internal.pc.completions.OverrideCompletions$.contribute(OverrideCompletions.scala:127)
	scala.meta.internal.pc.completions.Completions.advancedCompletions(Completions.scala:460)
	scala.meta.internal.pc.completions.Completions.completions(Completions.scala:183)
	scala.meta.internal.pc.completions.CompletionProvider.completions(CompletionProvider.scala:86)
	scala.meta.internal.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:136)
	scala.meta.internal.pc.CompilerAccess.withSharedCompiler(CompilerAccess.scala:146)
	scala.meta.internal.pc.CompilerAccess.$anonfun$1(CompilerAccess.scala:92)
	scala.meta.internal.pc.CompilerAccess.onCompilerJobQueue$$anonfun$1(CompilerAccess.scala:232)
	scala.meta.internal.pc.CompilerJobQueue$Job.run(CompilerJobQueue.scala:152)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	java.base/java.lang.Thread.run(Thread.java:834)
```
#### Short summary: 

java.lang.ClassCastException: class scala.meta.internal.pc.printer.ShortenedNames$PrettyType cannot be cast to class dotty.tools.dotc.core.Types$TypeRef (scala.meta.internal.pc.printer.ShortenedNames$PrettyType and dotty.tools.dotc.core.Types$TypeRef are in unnamed module of loader java.net.URLClassLoader @4d5f7069)