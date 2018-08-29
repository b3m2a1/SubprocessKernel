package com.b3;
import com.wolfram.jlink.*;

public class SubprocessKernelListener implements PacketListener {

    @Override
    public boolean packetArrived(PacketArrivedEvent evt) throws MathLinkException {

        if (evt.getPktType() == MathLink.TEXTPKT) {
            KernelLink ml = (KernelLink) evt.getSource();
            System.out.println(ml.getString());
        }
        return true;

    }

}


