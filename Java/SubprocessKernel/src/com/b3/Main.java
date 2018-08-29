package com.b3;

import com.wolfram.jlink.MathLinkException;

public class Main {

    public static void main(String[] args) {
        SubprocessKernelListener listener = new SubprocessKernelListener();

        String[] argv = {""};

        String bin = "/Applications/Mathematica.app/Contents/MacOS/WolframKernel";

        try {
            SubprocessKernel kernel = SubprocessKernelFactory.createKernel(bin);
            kernel.init().interact();
        } catch (MathLinkException e) {
            System.out.println("KernelLink failed to open");
            return;
        };

    }
}
