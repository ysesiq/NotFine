package jss.notfine.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import jss.notfine.config.NotFineConfig;
import jss.notfine.mixinplugin.NotFineEarlyMixins;
import jss.notfine.config.MCPatcherForgeConfig;
import net.xiaoyu233.fml.FishModLoader;

/**
 * Adapted from Hodgepodge
 */
public enum AsmTransformers {

    RENDERBLOCKS("RenderBlocks transformer", () -> MCPatcherForgeConfig.instance().customColorsEnabled, Side.CLIENT,
        "jss.notfine.asm.RenderBlocksTransformer"),
    WORLDRENDERER("WorldRenderer transformer", () -> NotFineConfig.renderPass, Side.CLIENT,
        "jss.notfine.asm.WorldRendererTransformer");

    private final Supplier<Boolean> applyIf;
    private final Side side;
    private final String[] transformerClasses;

    AsmTransformers(@SuppressWarnings("unused") String description, Supplier<Boolean> applyIf, Side side,
        String... transformers) {
        this.applyIf = applyIf;
        this.side = side;
        this.transformerClasses = transformers;
    }

    private boolean shouldBeLoaded() {
        return applyIf.get() && shouldLoadSide();
    }

    private boolean shouldLoadSide() {
        return side == Side.BOTH || (side == Side.SERVER && FishModLoader.isServer())
            || (side == Side.CLIENT && !FishModLoader.isServer());
    }

    public static String[] getTransformers() {
        final List<String> list = new ArrayList<>();
        for (AsmTransformers transformer : values()) {
            if (transformer.shouldBeLoaded()) {
                NotFineEarlyMixins.mcpfLogger.info("Loading transformer {}", (Object[]) transformer.transformerClasses);
                list.addAll(Arrays.asList(transformer.transformerClasses));
            } else {
                NotFineEarlyMixins.mcpfLogger.info("Not loading transformer {}", (Object[]) transformer.transformerClasses);
            }
        }
        return list.toArray(new String[0]);
    }

    private enum Side {
        BOTH,
        CLIENT,
        SERVER
    }
}
