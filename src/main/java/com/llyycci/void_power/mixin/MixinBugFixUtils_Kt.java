package com.llyycci.void_power.mixin;

//@Mixin(value = BugFixUtil.class, remap = false)
public abstract class MixinBugFixUtils_Kt {

    /*
    @Inject(method = "isCollisionBoxToBig", at = @At("RETURN"), cancellable = true)
    public void TraceCall(AABB aabb, CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue()){
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            // 去map集合里面拿当前线程的栈堆跟踪信息数组
            StackTraceElement[] stackTraceElements = allStackTraces.get(Thread.currentThread());
            System.out.println("TOO BIG");
            System.out.println(aabb.toString());
            System.out.println(Arrays.toString(stackTraceElements));
            cir.cancel();
        }
    }
     */

}
