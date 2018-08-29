package com.b3;
import com.wolfram.jlink.*;

public class SubprocessKernelFactory {

    /*
        Build the actual kernel links.
        Just for modularization create a private constructor for each mode.
        Then build public factory functions with all the possible args.
    */

    private static SubprocessKernel iCreateKernel (String bin, SubprocessKernelListener list) throws MathLinkException {
        String[] argv = {"-linkmode", "launch", "-linkname", bin};
        KernelLink ker = MathLinkFactory.createKernelLink(argv);
        return new SubprocessKernel(ker, list);
    };

    private  static SubprocessKernel iConnectKernel(String kl, SubprocessKernelListener list) throws MathLinkException {
        String[] argv = {"-linkmode", "connect", "-linkname", kl};
        KernelLink ker = MathLinkFactory.createKernelLink(argv);
        return new SubprocessKernel(ker, list);
    };

    private  static SubprocessKernel iListenKernel(String kl, SubprocessKernelListener list) throws MathLinkException {
        String[] argv = {"-linkmode", "listen", "-linkname", kl};
        KernelLink ker = MathLinkFactory.createKernelLink(argv);
        return new SubprocessKernel(ker, list);
    };

    private  static SubprocessKernel iPConnectKernel(String kl, SubprocessKernelListener list) throws MathLinkException {
        String[] argv = {"-linkmode", "parentconnect", "-linkname", kl};
        KernelLink ker = MathLinkFactory.createKernelLink(argv);
        return new SubprocessKernel(ker, list);
    };

    /*
        Mode for launching a new kernel
    */

    public  static SubprocessKernel createKernel(String bin) throws MathLinkException {
        SubprocessKernelListener list = new SubprocessKernelListener();
        return iCreateKernel(bin, list);
    };

    public  static SubprocessKernel createKernel(String bin, SubprocessKernelListener list) throws MathLinkException {
        return iCreateKernel(bin, list);
    };

    /*
        Mode for connecting to an existing
    */

    public  static SubprocessKernel connectKernel(String bin) throws MathLinkException {
        SubprocessKernelListener list = new SubprocessKernelListener();
        return iConnectKernel(bin, list);
    };

    public  static SubprocessKernel connectKernel(String bin, SubprocessKernelListener list) throws MathLinkException {
        return iConnectKernel(bin, list);
    };

    /*
        Mode for listening to an existing
    */

    public  static SubprocessKernel listenKernel(String bin) throws MathLinkException {
        SubprocessKernelListener list = new SubprocessKernelListener();
        return iListenKernel(bin, list);
    };

    public  static SubprocessKernel listenKernel(String bin, SubprocessKernelListener list) throws MathLinkException {
        return iListenKernel(bin, list);
    };

}
