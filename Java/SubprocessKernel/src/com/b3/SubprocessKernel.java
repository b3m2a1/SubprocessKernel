package com.b3;
import com.wolfram.jlink.*;
import java.nio.file.*;
import java.util.Scanner;

public class SubprocessKernel {

    private String kernelName;
    private KernelLink link;
    private SubprocessKernelListener listener;
    private Boolean initialized;

    public SubprocessKernel ( KernelLink kernel,  SubprocessKernelListener listener ) {

        // Copped directly from the SampleProgram JLink example

        link = kernel;
        this.listener = listener;
        link.addPacketListener(listener);

        try {
            kernelName = link.name();
        } catch (MathLinkException e) {
            System.out.println("KernelLink failed to open");
            return;
        }

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), "Resources", "SubprocessKernel.wl");
        String subProcWL = filePath.toString();

//        // For dev
//        subProcWL = "~/Documents/GitHub/mathematica-tools/SubprocessKernel.wl";

        String kernelInit = String.join("",
                "Get[\"", subProcWL, "\"];"
        );
        String subREPLConfig = String.join("\n",
                String.join("",
                        "$SubprocessREPLSettings[\"Links\"]",
                        "=",
                        "{\"",
                        kernelName,
                        "\"}",
                        ";"
                ),
                String.join("",
                        "$SubprocessREPLSettings[\"InitializationMessage\"]",
                        "=",
                        "None",
                        ";"
                )
        );

        try {
            link.evaluate(kernelInit);
            link.discardAnswer();
            link.evaluateToInputForm(subREPLConfig, 0);
        } catch (MathLinkException e) {
            System.out.println("KernelLink is dead");
            return;
        }

    }

    public SubprocessKernel init () {

        String subREPLInit = "SubprocessKernel`OpenSubprocessNotebook[]";
        link.evaluateToInputForm(subREPLInit, 0);
        initialized = true;
        return this;

//        String subREPLQuery = "SubprocessKernel`$SubprocessREPLSettings";
//        String out2 = link.evaluateToInputForm(subREPLQuery, 0);
//        System.out.println(out2);

    }

    public String evaluateToInputForm (String input) {
        return link.evaluateToInputForm(input, 0);
    }
    public String evaluateToInputForm(Expr input) {
        return link.evaluateToInputForm(input, 0);
    }

    public String evaluateToOutputForm (String input) {
        return link.evaluateToOutputForm(input, 0);
    }
    public String evaluateToOutputForm(Expr input) {
        return link.evaluateToOutputForm(input, 0);
    }

    public void evaluateSubmit (String input) {
        try {
            link.evaluate(input);
        } catch (MathLinkException e) {
            System.out.println("Failed to place evaluation on queue");
        }
    }
    public void evaluateSubmit (Expr input) {
        try {
            link.evaluate(input);
        } catch (MathLinkException e) {
            System.out.println("Failed to place evaluation on queue");
        }
    }

    public Expr getEvaluationResult () {

        try {
            int ans = link.waitForAnswer();
            return link.getExpr();
        } catch (MathLinkException e) {
            System.out.println("Failed to get evaluation result");
            return new Expr(Expr.SYMBOL, "SubprocessKernel`$SubprocessKernelFailure");
        }

    }
    public String getEvaluationResultAsString () {

        try {
            int ans = link.waitForAnswer();
            return link.getString();
        } catch (MathLinkException e) {
            System.out.println("Failed to place evaluation on queue");
            return "SubprocessKernel`$SubprocessKernelFailure";
        }

    }

    // Interactive subkernel
    public void interact () {
        // Need to read from stdin; push as eval result; read back;

        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        String command = null;
        String output = null;
        boolean continueFlag = true;
        while (continueFlag) {
            command = scanner.next();
            if (command.equals("Quit") || command.startsWith("Quit[]")) {
                link.terminateKernel();
                continueFlag=false;
            } else {
                output = evaluateToInputForm(command);
                if (!(output.equals("Null") || output.equals("") )) {
                    System.out.println(output);
                }

            }
        }

    };

}
