package com.llyycci.void_power.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.llyycci.void_power.patched.IPatchedTermAccessor;

import dan200.computercraft.core.terminal.Terminal;

@Mixin(value = Terminal.class, remap = false)
public class MixinTerminal implements IPatchedTermAccessor {
    @Unique
    private boolean TransMode = true;

    @Unique
    private char TransColor = 'f';

    @Override
    public boolean void_power$GetTransMode() {
        return TransMode;
    }

    @Override
    public char void_power$GetTransColor() {
        return TransColor;
    }

    @Override
    public void void_power$SetTransMode(boolean m) {
        TransMode = m;
    }

    @Override
    public void void_power$SetTransColor(char c) {
        TransColor = c;
    }
}
