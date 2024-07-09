package top.xdi8.mod.fonts.forge;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Objects;

final class LoadFontsConfigASM implements Runnable {
    private final ClassNode cls;

    public LoadFontsConfigASM(ClassNode cls) {
        this.cls = cls;
    }

    public void run() {
        var method = cls.methods.stream()
                .filter(m1 -> Objects.equals("onGetSelectedTypeface", m1.name) &&
                        Objects.equals("()Licyllis/modernui/text/Typeface;", m1.desc))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unable to find method " + "onGetSelectedTypeface" + ' ' + "()Licyllis/modernui/text/Typeface;"));

        for (AbstractInsnNode node : method.instructions) {
            if (node instanceof MethodInsnNode m && m.getOpcode() == Opcodes.INVOKESPECIAL &&
            "<init>".equals(m.name) && "java/util/LinkedHashSet".equals(m.owner)) {
                InsnList insnList = new InsnList(); {
                    insnList.add(new InsnNode(Opcodes.DUP));
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                            "top/xdi8/mod/fonts/Xdi8FontsMod",
                            "loadFont",
                            "(Ljava/util/Set;)V"));
                }
                method.instructions.insert(m, insnList);
            }
        }
    }
}
