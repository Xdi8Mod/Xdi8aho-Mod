package top.xdi8.mod.fonts.asm;

import net.minecraftforge.coremod.api.ASMAPI;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import top.xdi8.mod.fonts.Xdi8FontsMod;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

final class HackGlyphProviderBuildType implements Consumer<ClassNode> {
    private static final String clazz = "net/minecraft/client/gui/font/providers/GlyphProviderBuilderType";
    private static final String $values = "m_169108_";
    private static final String valuesDesc = "()[Lnet/minecraft/client/gui/font/providers/GlyphProviderBuilderType;";
    private static final String clazzDesc = 'L' + clazz + ';';
    private static final String initDesc = "(Ljava/lang/String;ILjava/lang/String;Ljava/util/function/Function;)";
    private static final String factoryView = "top/xdi8/mod/fonts/asm/FactoryView";
    private static final String getFactory = "()Ljava/util/function/Function;";

    @Override
    public void accept(ClassNode classNode) {
        int thisId = (int) classNode.fields.stream()
                .filter(f -> (f.access & Opcodes.ACC_ENUM) != 0 &&
                        clazzDesc.equals(f.desc))
                .count();
        // add field
        classNode.fields.add(new FieldNode(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL + Opcodes.ACC_ENUM,
                "asm-gen$xdi8-fonts$TTF_NT", clazzDesc, null, null));
        insertCons(classNode, thisId);
        insertValues(classNode, thisId);
    }

    private void insertValues(ClassNode classNode, int thisId) {
        var valuesName = ASMAPI.mapMethod($values);
        var valuesMethod = classNode.methods.stream()
                .filter(m -> valuesName.equals(m.name) && valuesDesc.equals(m.desc))
                .findAny()
                .orElseThrow();

        InsnList instructions = valuesMethod.instructions;
        var newArray = firstNewArray(instructions);

        // increment size
        InsnList l = new InsnList(); {
            l.add(new InsnNode(Opcodes.ICONST_1));
            l.add(new InsnNode(Opcodes.IADD));
        }
        instructions.insertBefore(newArray, l);

        // insert this
        l = new InsnList(); {
            l.add(new InsnNode(Opcodes.DUP));
            l.add(new IntInsnNode(Opcodes.SIPUSH, thisId));
            l.add(new FieldInsnNode(Opcodes.GETSTATIC, clazz, "asm-gen$xdi8-fonts$TTF_NT", clazzDesc));
            l.add(new InsnNode(Opcodes.AASTORE));
        }
        instructions.insert(newArray, l);
    }

    private TypeInsnNode firstNewArray(InsnList nodes) {
        for (var n = nodes.getFirst(); n != nodes.getLast(); n = n.getNext()) {
            if (n instanceof TypeInsnNode tn && tn.getOpcode() == Opcodes.ANEWARRAY && clazz.equals(tn.desc))
                return tn;
        }
        throw new NoSuchElementException("Can't find ANEWARRAY[ " + clazz + "] opcode in $values()");
    }

    private void insertCons(ClassNode classNode, int thisId) {
        var consMethod = classNode.methods.stream()
                .filter(m -> "<clinit>".equals(m.name) && "()V".equals(m.desc))
                .findAny()
                .orElseThrow();
        // init field
        InsnList il = new InsnList(); {
            il.add(new TypeInsnNode(Opcodes.NEW, clazz));
            il.add(new InsnNode(Opcodes.DUP));
            // (Ljava/lang/String;ILjava/lang/String;Ljava/util/function/Function;)
            il.add(new LdcInsnNode(Xdi8FontsMod.PROVIDER_NAME));
            il.add(new LdcInsnNode(thisId));
            il.add(new LdcInsnNode("asm-gen$xdi8-fonts$TTF_NT"));
            il.add(new MethodInsnNode(Opcodes.INVOKESTATIC, factoryView, "getFactory", getFactory));
            il.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, clazz, "<init>", initDesc));
            il.add(new FieldInsnNode(Opcodes.PUTSTATIC, clazz, "asm-gen$xdi8-fonts$TTF_NT", clazzDesc));
        }
        consMethod.instructions.insert(il);
    }
}
