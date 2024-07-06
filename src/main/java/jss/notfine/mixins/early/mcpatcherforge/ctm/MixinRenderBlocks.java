package jss.notfine.mixins.early.mcpatcherforge.ctm;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import net.minecraft.Block;
import net.minecraft.BlockAnvil;
import net.minecraft.BlockBrewingStand;
import net.minecraft.BlockDoublePlant;
import net.minecraft.BlockFlowerPot;
import net.minecraft.BlockGrass;
import net.minecraft.BlockHopper;
import net.minecraft.BlockPane;
import net.minecraft.BlockRailBase;
import net.minecraft.BlockRedstoneDiode;
import net.minecraft.BlockStainedGlassPane;
import net.minecraft.EntityRenderer;
import net.minecraft.RenderBlocks;
import net.minecraft.Tessellator;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.ctm.CTMUtils;
import com.prupe.mcpatcher.ctm.GlassPaneRenderer;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderBlocks {

    @Shadow
    public IBlockAccess blockAccess;

    @Shadow
    public Icon overrideBlockTexture;

    @Shadow
    public abstract Icon getBlockIcon(Block block, IBlockAccess access, int x, int y, int z, int side);

    @Shadow
    public abstract Icon getBlockIconFromSideAndMetadata(Block block, int side, int meta);

    @Shadow
    public abstract Icon getBlockIconFromSide(Block block, int side);

    @Shadow
    public abstract boolean hasOverrideBlockTexture();

    @Shadow
    public abstract Icon getIconSafe(Icon texture);

    @Redirect(
        method = "renderBlockMinecartTrack(Lnet/minecraft/BlockRailBase;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/Block;II)Lnet/minecraft/Icon;"))
    private Icon modifyRenderBlockMinecartTrack(RenderBlocks instance, Block block, int side, int meta,
        BlockRailBase specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = {
            "renderBlockVine(Lnet/minecraft/Block;III)Z",
            "renderBlockLilyPad(Lnet/minecraft/Block;III)Z",
            "renderBlockLadder(Lnet/minecraft/Block;III)Z",
            "renderBlockTripWireSource(Lnet/minecraft/Block;III)Z",
            "renderBlockLever(Lnet/minecraft/Block;III)Z",
            "renderBlockTripWire(Lnet/minecraft/Block;III)Z"
        },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/Block;I)Lnet/minecraft/Icon;"))
    private Icon redirectGetBlockIconFromSide(RenderBlocks instance, Block block, int side, Block specializedBlock,
        int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockBrewingStand(Lnet/minecraft/BlockBrewingStand;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/Block;II)Lnet/minecraft/Icon;"))
    private Icon modifyRenderBlockBrewingStand(RenderBlocks instance, Block block, int side, int meta,
        BlockBrewingStand specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockFlowerpot(Lnet/minecraft/BlockFlowerPot;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/Block;I)Lnet/minecraft/Icon;"))
    private Icon modifyRenderBlockFlowerpot(RenderBlocks instance, Block block, int side,
        BlockFlowerPot specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockAnvilRotate(Lnet/minecraft/BlockAnvil;IIIIFFFFZZI)F",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/Block;II)Lnet/minecraft/Icon;"))
    private Icon modifyRenderBlockAnvilRotate(RenderBlocks instance, Block block, int side, int meta,
        BlockAnvil specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockRedstoneDiodeMetadata(Lnet/minecraft/BlockRedstoneDiode;IIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/Block;II)Lnet/minecraft/Icon;"))
    private Icon modifyRenderRedstoneDiodeMetadata(RenderBlocks instance, Block block, int side, int meta,
        BlockRedstoneDiode specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Significant deviation from Vanilla
     */
    @SuppressWarnings("DuplicatedCode")
    @Overwrite
    public boolean renderBlockStainedGlassPane(Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(this.blockAccess, x, y, z));
        int i1 = block.colorMultiplier(this.blockAccess, x, y, z);
        float f = (float) (i1 >> 16 & 255) / 255.0F;
        float f1 = (float) (i1 >> 8 & 255) / 255.0F;
        float f2 = (float) (i1 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        tessellator.setColorOpaque_F(f, f1, f2);
        boolean flag5 = block instanceof BlockStainedGlassPane;
        Icon iicon;
        Icon iicon1;

        if (this.hasOverrideBlockTexture()) {
            iicon = this.overrideBlockTexture;
            iicon1 = this.overrideBlockTexture;
        } else {
            int j1 = this.blockAccess.getBlockMetadata(x, y, z);
            // Change 1
            iicon = ((this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, 0, j1)
                : this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            iicon1 = flag5 ? ((BlockStainedGlassPane) block).func_150104_b(j1) : ((BlockPane) block).func_150097_e();
        }
        // Change 2
        double n7 = iicon.getMinU();
        double n8 = iicon.getInterpolatedU(7.0);
        double n9 = iicon.getInterpolatedU(9.0);
        double n10 = iicon.getMaxU();
        double n11 = iicon.getMinV();
        double n12 = iicon.getMaxV();

        double n13 = iicon1.getInterpolatedU(7.0);
        double n14 = iicon1.getInterpolatedU(9.0);
        double n15 = iicon1.getMinV();
        double n16 = iicon1.getMaxV();
        double n17 = iicon1.getInterpolatedV(7.0);
        double n18 = iicon1.getInterpolatedV(9.0);

        double n20 = x + 1;
        double n22 = z + 1;
        double n23 = x + 0.5 - 0.0625;
        double n24 = x + 0.5 + 0.0625;
        double n25 = z + 0.5 - 0.0625;
        double n26 = z + 0.5 + 0.0625;

        boolean connectNorth = ((BlockPane) block).canPaneConnectTo(this.blockAccess, x, y, z - 1, NORTH);
        boolean connectSouth = ((BlockPane) block).canPaneConnectTo(this.blockAccess, x, y, z + 1, SOUTH);
        boolean connectWest = ((BlockPane) block).canPaneConnectTo(this.blockAccess, x - 1, y, z, WEST);
        boolean connectEast = ((BlockPane) block).canPaneConnectTo(this.blockAccess, x + 1, y, z, EAST);

        // Change 3
        GlassPaneRenderer.renderThick(
            (RenderBlocks) (Object) this,
            block,
            iicon,
            x,
            y,
            z,
            connectNorth,
            connectSouth,
            connectWest,
            connectEast);
        boolean flag4 = !connectNorth && !connectSouth && !connectWest && !connectEast;
        // Change 4 (full on replacement)
        if (connectWest || flag4) {
            if (connectWest && connectEast) {
                if (!connectNorth) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n20, y + 0.999, n25, n10, n11);
                        tessellator.addVertexWithUV(n20, y + 0.001, n25, n10, n12);
                        tessellator.addVertexWithUV(x, y + 0.001, n25, n7, n12);
                        tessellator.addVertexWithUV(x, y + 0.999, n25, n7, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(x, y + 0.001, n25, n7, n12);
                    tessellator.addVertexWithUV(x, y + 0.999, n25, n7, n11);
                    tessellator.addVertexWithUV(n20, y + 0.999, n25, n10, n11);
                    tessellator.addVertexWithUV(n20, y + 0.001, n25, n10, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
                }
                if (!connectSouth) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(x, y + 0.999, n26, n7, n11);
                        tessellator.addVertexWithUV(x, y + 0.001, n26, n7, n12);
                        tessellator.addVertexWithUV(n20, y + 0.001, n26, n10, n12);
                        tessellator.addVertexWithUV(n20, y + 0.999, n26, n10, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(x, y + 0.999, n26, n7, n11);
                    tessellator.addVertexWithUV(x, y + 0.001, n26, n7, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n8, n11);
                    tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                    tessellator.addVertexWithUV(n20, y + 0.001, n26, n10, n12);
                    tessellator.addVertexWithUV(n20, y + 0.999, n26, n10, n11);
                }
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(x, y + 0.999, n26, n14, n15);
                    tessellator.addVertexWithUV(n20, y + 0.999, n26, n14, n16);
                    tessellator.addVertexWithUV(n20, y + 0.999, n25, n13, n16);
                    tessellator.addVertexWithUV(x, y + 0.999, n25, n13, n15);
                }
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n20, y + 0.001, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y + 0.001, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y + 0.001, n25, n14, n15);
                    tessellator.addVertexWithUV(n20, y + 0.001, n25, n14, n16);
                }
            } else {
                if (!connectNorth && !flag4) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
                        tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
                        tessellator.addVertexWithUV(x, y + 0.001, n25, n7, n12);
                        tessellator.addVertexWithUV(x, y + 0.999, n25, n7, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(x, y + 0.001, n25, n7, n12);
                    tessellator.addVertexWithUV(x, y + 0.999, n25, n7, n11);
                }
                if (!connectSouth && !flag4) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(x, y + 0.999, n26, n7, n11);
                        tessellator.addVertexWithUV(x, y + 0.001, n26, n7, n12);
                        tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                        tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(x, y + 0.999, n26, n7, n11);
                    tessellator.addVertexWithUV(x, y + 0.001, n26, n7, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n8, n11);
                }
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(x, y + 0.999, n26, n14, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n14, n17);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n13, n17);
                    tessellator.addVertexWithUV(x, y + 0.999, n25, n13, n15);
                }
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n13, n17);
                    tessellator.addVertexWithUV(x, y + 0.001, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y + 0.001, n25, n14, n15);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n14, n17);
                }
            }
        } else if (!connectNorth && !connectSouth && !GlassPaneRenderer.skipPaneRendering) {
            tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
            tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
            tessellator.addVertexWithUV(n23, y + 0.001, n26, n9, n12);
            tessellator.addVertexWithUV(n23, y + 0.999, n26, n9, n11);
        }
        if ((connectEast || flag4) && !connectWest) {
            if (!connectSouth && !flag4) {
                if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n8, n12);
                    tessellator.addVertexWithUV(n20, y + 0.001, n26, n10, n12);
                    tessellator.addVertexWithUV(n20, y + 0.999, n26, n10, n11);
                }
            } else if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                tessellator.addVertexWithUV(n20, y + 0.001, n26, n10, n12);
                tessellator.addVertexWithUV(n20, y + 0.999, n26, n10, n11);
            }
            if (!connectNorth && !flag4) {
                if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n20, y + 0.999, n25, n10, n11);
                    tessellator.addVertexWithUV(n20, y + 0.001, n25, n10, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                }
            } else if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n20, y + 0.999, n25, n10, n11);
                tessellator.addVertexWithUV(n20, y + 0.001, n25, n10, n12);
                tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
                tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
            }
            if (!GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, n26, n14, n18);
                tessellator.addVertexWithUV(n20, y + 0.999, n26, n14, n15);
                tessellator.addVertexWithUV(n20, y + 0.999, n25, n13, n15);
                tessellator.addVertexWithUV(n24, y + 0.999, n25, n13, n18);
            }
            if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                tessellator.addVertexWithUV(n20, y + 0.001, n26, n13, n16);
                tessellator.addVertexWithUV(n24, y + 0.001, n26, n13, n18);
                tessellator.addVertexWithUV(n24, y + 0.001, n25, n14, n18);
                tessellator.addVertexWithUV(n20, y + 0.001, n25, n14, n16);
            }
        } else if (!connectEast && !connectNorth && !connectSouth && !GlassPaneRenderer.skipPaneRendering) {
            tessellator.addVertexWithUV(n24, y + 0.999, n26, n8, n11);
            tessellator.addVertexWithUV(n24, y + 0.001, n26, n8, n12);
            tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
            tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
        }
        if (connectNorth || flag4) {
            if (connectNorth && connectSouth) {
                if (!connectWest) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n23, y + 0.999, z, n7, n11);
                        tessellator.addVertexWithUV(n23, y + 0.001, z, n7, n12);
                        tessellator.addVertexWithUV(n23, y + 0.001, n22, n10, n12);
                        tessellator.addVertexWithUV(n23, y + 0.999, n22, n10, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, z, n7, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, z, n7, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n9, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n9, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n22, n10, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n22, n10, n11);
                }
                if (!connectEast) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n24, y + 0.999, n22, n10, n11);
                        tessellator.addVertexWithUV(n24, y + 0.001, n22, n10, n12);
                        tessellator.addVertexWithUV(n24, y + 0.001, z, n7, n12);
                        tessellator.addVertexWithUV(n24, y + 0.999, z, n7, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, z, n7, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, z, n7, n11);
                    tessellator.addVertexWithUV(n24, y + 0.999, n22, n10, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n22, n10, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                }
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, z, n14, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, z, n13, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, n22, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 0.999, n22, n14, n16);
                }
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.001, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y + 0.001, z, n14, n15);
                    tessellator.addVertexWithUV(n24, y + 0.001, n22, n14, n16);
                    tessellator.addVertexWithUV(n23, y + 0.001, n22, n13, n16);
                }
            } else {
                if (!connectWest && !flag4) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n23, y + 0.999, z, n7, n11);
                        tessellator.addVertexWithUV(n23, y + 0.001, z, n7, n12);
                        tessellator.addVertexWithUV(n23, y + 0.001, n26, n9, n12);
                        tessellator.addVertexWithUV(n23, y + 0.999, n26, n9, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, z, n7, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, z, n7, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                }
                if (!connectEast && !flag4) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                        tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                        tessellator.addVertexWithUV(n24, y + 0.001, z, n7, n12);
                        tessellator.addVertexWithUV(n24, y + 0.999, z, n7, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, z, n7, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, z, n7, n11);
                }
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, z, n14, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, z, n13, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n13, n17);
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n14, n17);
                }
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.001, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y + 0.001, z, n14, n15);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n14, n17);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n13, n17);
                }
            }
        } else if (!connectEast && !connectWest && !GlassPaneRenderer.skipPaneRendering) {
            tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
            tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
            tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
            tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
        }
        if ((connectSouth || flag4) && !connectNorth) {
            if (!connectWest && !flag4) {
                if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n22, n10, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n22, n10, n11);
                }
            } else if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n23, y + 0.999, n26, n9, n11);
                tessellator.addVertexWithUV(n23, y + 0.001, n26, n9, n12);
                tessellator.addVertexWithUV(n23, y + 0.001, n22, n10, n12);
                tessellator.addVertexWithUV(n23, y + 0.999, n22, n10, n11);
            }
            if (!connectEast && !flag4) {
                if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, n22, n10, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n22, n10, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n8, n11);
                }
            } else if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, n22, n10, n11);
                tessellator.addVertexWithUV(n24, y + 0.001, n22, n10, n12);
                tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
            }
            if (!GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, n26, n14, n18);
                tessellator.addVertexWithUV(n23, y + 0.999, n26, n13, n18);
                tessellator.addVertexWithUV(n23, y + 0.999, n22, n13, n16);
                tessellator.addVertexWithUV(n24, y + 0.999, n22, n14, n16);
            }
            if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                tessellator.addVertexWithUV(n23, y + 0.001, n26, n13, n18);
                tessellator.addVertexWithUV(n24, y + 0.001, n26, n14, n18);
                tessellator.addVertexWithUV(n24, y + 0.001, n22, n14, n16);
                tessellator.addVertexWithUV(n23, y + 0.001, n22, n13, n16);
            }
        } else if (!connectSouth && !connectEast && !connectWest && !GlassPaneRenderer.skipPaneRendering) {
            tessellator.addVertexWithUV(n23, y + 0.999, n26, n8, n11);
            tessellator.addVertexWithUV(n23, y + 0.001, n26, n8, n12);
            tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
            tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
        }
        if (!GlassPaneRenderer.skipTopEdgeRendering) {
            tessellator.addVertexWithUV(n24, y + 0.999, n25, n14, n17);
            tessellator.addVertexWithUV(n23, y + 0.999, n25, n13, n17);
            tessellator.addVertexWithUV(n23, y + 0.999, n26, n13, n18);
            tessellator.addVertexWithUV(n24, y + 0.999, n26, n14, n18);
        }
        if (!GlassPaneRenderer.skipBottomEdgeRendering) {
            tessellator.addVertexWithUV(n23, y + 0.001, n25, n13, n17);
            tessellator.addVertexWithUV(n24, y + 0.001, n25, n14, n17);
            tessellator.addVertexWithUV(n24, y + 0.001, n26, n14, n18);
            tessellator.addVertexWithUV(n23, y + 0.001, n26, n13, n18);
        }
        if (flag4) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(x, y + 0.999, n25, n8, n11);
                tessellator.addVertexWithUV(x, y + 0.001, n25, n8, n12);
                tessellator.addVertexWithUV(x, y + 0.001, n26, n9, n12);
                tessellator.addVertexWithUV(x, y + 0.999, n26, n9, n11);
                tessellator.addVertexWithUV(n20, y + 0.999, n26, n8, n11);
                tessellator.addVertexWithUV(n20, y + 0.001, n26, n8, n12);
                tessellator.addVertexWithUV(n20, y + 0.001, n25, n9, n12);
                tessellator.addVertexWithUV(n20, y + 0.999, n25, n9, n11);
            }
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, z, n9, n11);
                tessellator.addVertexWithUV(n24, y + 0.001, z, n9, n12);
                tessellator.addVertexWithUV(n23, y + 0.001, z, n8, n12);
                tessellator.addVertexWithUV(n23, y + 0.999, z, n8, n11);
                tessellator.addVertexWithUV(n23, y + 0.999, n22, n8, n11);
                tessellator.addVertexWithUV(n23, y + 0.001, n22, n8, n12);
                tessellator.addVertexWithUV(n24, y + 0.001, n22, n9, n12);
                tessellator.addVertexWithUV(n24, y + 0.999, n22, n9, n11);
            }
        }

        return true;
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Significant deviation from Vanilla
     */
    @SuppressWarnings("DuplicatedCode")
    @Overwrite
    public boolean renderBlockPane(BlockPane block, int x, int y, int z) {
        int r = this.blockAccess.getHeight();
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(this.blockAccess, x, y, z));
        int d = block.colorMultiplier(this.blockAccess, x, y, z);
        float n = (d >> 16 & 0xFF) / 255.0f;
        float n2 = (d >> 8 & 0xFF) / 255.0f;
        float n3 = (d & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float n4 = (n * 30.0f + n2 * 59.0f + n3 * 11.0f) / 100.0f;
            float n5 = (n * 30.0f + n2 * 70.0f) / 100.0f;
            float n6 = (n * 30.0f + n3 * 70.0f) / 100.0f;
            n = n4;
            n2 = n5;
            n3 = n6;
        }
        tessellator.setColorOpaque_F(n, n2, n3);
        Icon d2;
        Icon rf;
        if (this.hasOverrideBlockTexture()) {
            d2 = this.overrideBlockTexture;
            rf = this.overrideBlockTexture;
        } else {
            int e = this.blockAccess.getBlockMetadata(x, y, z);
            d2 = ((this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, 0, e)
                : this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            rf = block.func_150097_e();
        }
        double n7 = d2.getMinU();
        double n8 = d2.getInterpolatedU(8.0);
        double n9 = d2.getMaxU();
        double n10 = d2.getMinV();
        double n11 = d2.getMaxV();
        double n12 = rf.getInterpolatedU(7.0);
        double n13 = rf.getInterpolatedU(9.0);
        double n14 = rf.getMinV();
        double n15 = rf.getInterpolatedV(8.0);
        double n16 = rf.getMaxV();
        double n18 = x + 0.5;
        double n19 = x + 1;
        double n21 = z + 0.5;
        double n22 = z + 1;
        double n23 = x + 0.5 - 0.0625;
        double n24 = x + 0.5 + 0.0625;
        double n25 = z + 0.5 - 0.0625;
        double n26 = z + 0.5 + 0.0625;

        // Slightly different due to forge
        boolean a2 = block.canPaneConnectTo(this.blockAccess, x, y, z - 1, NORTH);
        boolean a3 = block.canPaneConnectTo(this.blockAccess, x, y, z + 1, SOUTH);
        boolean a4 = block.canPaneConnectTo(this.blockAccess, x - 1, y, z, WEST);
        boolean a5 = block.canPaneConnectTo(this.blockAccess, x + 1, y, z, EAST);
        boolean a6 = block.shouldSideBeRendered(this.blockAccess, x, y + 1, z, 1);
        boolean a7 = block.shouldSideBeRendered(this.blockAccess, x, y - 1, z, 0);

        GlassPaneRenderer.renderThin((RenderBlocks) (Object) this, block, d2, x, y, z, a2, a3, a4, a5);
        if ((a4 && a5) || (!a4 && !a5 && !a2 && !a3)) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(x, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(x, y, n21, n7, n11);
                tessellator.addVertexWithUV(n19, y, n21, n9, n11);
                tessellator.addVertexWithUV(n19, y + 1, n21, n9, n10);
                tessellator.addVertexWithUV(n19, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(n19, y, n21, n7, n11);
                tessellator.addVertexWithUV(x, y, n21, n9, n11);
                tessellator.addVertexWithUV(x, y + 1, n21, n9, n10);
            }
            if (a6) {
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n16);
                }
            } else {
                if (y < r - 1 && this.blockAccess.isAirBlock(x - 1, y + 1, z)
                    && !GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n15);
                }
                if (y < r - 1 && this.blockAccess.isAirBlock(x + 1, y + 1, z)
                    && !GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n14);
                }
            }
            if (a7) {
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n16);
                }
            } else {
                if (y > 1 && this.blockAccess.isAirBlock(x - 1, y - 1, z)
                    && !GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n15);
                }
                if (y > 1 && this.blockAccess.isAirBlock(x + 1, y - 1, z)) {
                    if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                        tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n14);
                        tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n15);
                        tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n15);
                        tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n14);
                        tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n14);
                        tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n15);
                        tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n15);
                        tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n14);
                    }
                }
            }
        } else if (a4) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(x, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(x, y, n21, n7, n11);
                tessellator.addVertexWithUV(n18, y, n21, n8, n11);
                tessellator.addVertexWithUV(n18, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n18, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(n18, y, n21, n7, n11);
                tessellator.addVertexWithUV(x, y, n21, n8, n11);
                tessellator.addVertexWithUV(x, y + 1, n21, n8, n10);
            }
            if (!a3 && !a2) {
                tessellator.addVertexWithUV(n18, y + 1, n26, n12, n14);
                tessellator.addVertexWithUV(n18, y, n26, n12, n16);
                tessellator.addVertexWithUV(n18, y, n25, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1, n25, n13, n14);
                tessellator.addVertexWithUV(n18, y + 1, n25, n12, n14);
                tessellator.addVertexWithUV(n18, y, n25, n12, n16);
                tessellator.addVertexWithUV(n18, y, n26, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1, n26, n13, n14);
            }
            if ((a6 || (y < r - 1 && this.blockAccess.isAirBlock(x - 1, y + 1, z)))
                && !GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n16);
                tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n16);
                tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n16);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n15);
            }
            if (a7 || (y > 1 && this.blockAccess.isAirBlock(x - 1, y - 1, z))) {
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n15);
                }
            }
        } else if (a5) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n18, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n18, y, n21, n8, n11);
                tessellator.addVertexWithUV(n19, y, n21, n9, n11);
                tessellator.addVertexWithUV(n19, y + 1, n21, n9, n10);
                tessellator.addVertexWithUV(n19, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n19, y, n21, n8, n11);
                tessellator.addVertexWithUV(n18, y, n21, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, n21, n9, n10);
            }
            if (!a3 && !a2) {
                tessellator.addVertexWithUV(n18, y + 1, n25, n12, n14);
                tessellator.addVertexWithUV(n18, y, n25, n12, n16);
                tessellator.addVertexWithUV(n18, y, n26, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1, n26, n13, n14);
                tessellator.addVertexWithUV(n18, y + 1, n26, n12, n14);
                tessellator.addVertexWithUV(n18, y, n26, n12, n16);
                tessellator.addVertexWithUV(n18, y, n25, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1, n25, n13, n14);
            }
            if ((a6 || (y < r - 1 && this.blockAccess.isAirBlock(x + 1, y + 1, z)))
                && !GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n14);
                tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n14);
                tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n14);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n14);
            }
            if ((a7 || (y > 1 && this.blockAccess.isAirBlock(x + 1, y - 1, z)))
                && !GlassPaneRenderer.skipBottomEdgeRendering) {
                tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n14);
                tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n14);
                tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n14);
                tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n14);
            }
        }
        if ((a2 && a3) || (!a4 && !a5 && !a2 && !a3)) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n18, y + 1, n22, n7, n10);
                tessellator.addVertexWithUV(n18, y, n22, n7, n11);
                tessellator.addVertexWithUV(n18, y, z, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, z, n9, n10);
                tessellator.addVertexWithUV(n18, y + 1, z, n7, n10);
                tessellator.addVertexWithUV(n18, y, z, n7, n11);
                tessellator.addVertexWithUV(n18, y, n22, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, n22, n9, n10);
            }
            if (a6) {
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n12, n16);
                }
            } else {
                if (y < r - 1 && this.blockAccess.isAirBlock(x, y + 1, z - 1)
                    && !GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n13, n15);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n12, n15);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n13, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n12, n15);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n12, n14);
                }
                if (y < r - 1 && this.blockAccess.isAirBlock(x, y + 1, z + 1)
                    && !GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n12, n15);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n13, n15);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n15);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n12, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n15);
                }
            }
            if (a7) {
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n16);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n16);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n13, n16);
                    tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n12, n16);
                }
            } else {
                if (y > 1 && this.blockAccess.isAirBlock(x, y - 1, z - 1)
                    && !GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n21, n13, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, n21, n12, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n21, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n12, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, n21, n12, n14);
                }
                if (y > 1 && this.blockAccess.isAirBlock(x, y - 1, z + 1)) {
                    if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                        tessellator.addVertexWithUV(n23, y - 0.005, n21, n12, n15);
                        tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n16);
                        tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n16);
                        tessellator.addVertexWithUV(n24, y - 0.005, n21, n13, n15);
                        tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n15);
                        tessellator.addVertexWithUV(n23, y - 0.005, n21, n12, n16);
                        tessellator.addVertexWithUV(n24, y - 0.005, n21, n13, n16);
                        tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n15);
                    }
                }
            }
        } else if (a2) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n18, y + 1, z, n7, n10);
                tessellator.addVertexWithUV(n18, y, z, n7, n11);
                tessellator.addVertexWithUV(n18, y, n21, n8, n11);
                tessellator.addVertexWithUV(n18, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n18, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(n18, y, n21, n7, n11);
                tessellator.addVertexWithUV(n18, y, z, n8, n11);
                tessellator.addVertexWithUV(n18, y + 1, z, n8, n10);
            }
            if (!a5 && !a4) {
                tessellator.addVertexWithUV(n23, y + 1, n21, n12, n14);
                tessellator.addVertexWithUV(n23, y, n21, n12, n16);
                tessellator.addVertexWithUV(n24, y, n21, n13, n16);
                tessellator.addVertexWithUV(n24, y + 1, n21, n13, n14);
                tessellator.addVertexWithUV(n24, y + 1, n21, n12, n14);
                tessellator.addVertexWithUV(n24, y, n21, n12, n16);
                tessellator.addVertexWithUV(n23, y, n21, n13, n16);
                tessellator.addVertexWithUV(n23, y + 1, n21, n13, n14);
            }
            if ((a6 || (y < r - 1 && this.blockAccess.isAirBlock(x, y + 1, z - 1)))
                && !GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n13, n14);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n13, n15);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n12, n15);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n12, n14);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n13, n14);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n13, n15);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n12, n15);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n12, n14);
            }
            if (a7 || (y > 1 && this.blockAccess.isAirBlock(x, y - 1, z - 1))) {
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n21, n13, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, n21, n12, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n21, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n12, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, n21, n12, n14);
                }
            }
        } else if (a3) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n18, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n18, y, n21, n8, n11);
                tessellator.addVertexWithUV(n18, y, n22, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, n22, n9, n10);
                tessellator.addVertexWithUV(n18, y + 1, n22, n8, n10);
                tessellator.addVertexWithUV(n18, y, n22, n8, n11);
                tessellator.addVertexWithUV(n18, y, n21, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, n21, n9, n10);
            }
            if (!a5 && !a4) {
                tessellator.addVertexWithUV(n24, y + 1, n21, n12, n14);
                tessellator.addVertexWithUV(n24, y, n21, n12, n16);
                tessellator.addVertexWithUV(n23, y, n21, n13, n16);
                tessellator.addVertexWithUV(n23, y + 1, n21, n13, n14);
                tessellator.addVertexWithUV(n23, y + 1, n21, n12, n14);
                tessellator.addVertexWithUV(n23, y, n21, n12, n16);
                tessellator.addVertexWithUV(n24, y, n21, n13, n16);
                tessellator.addVertexWithUV(n24, y + 1, n21, n13, n14);
            }
            if ((a6 || (y < r - 1 && this.blockAccess.isAirBlock(x, y + 1, z + 1)))
                && !GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n12, n15);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n16);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n16);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n13, n15);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n15);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n12, n16);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n13, n16);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n15);
            }
            if ((a7 || (y > 1 && this.blockAccess.isAirBlock(x, y - 1, z + 1)))
                && !GlassPaneRenderer.skipBottomEdgeRendering) {
                tessellator.addVertexWithUV(n23, y - 0.005, n21, n12, n15);
                tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n16);
                tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n16);
                tessellator.addVertexWithUV(n24, y - 0.005, n21, n13, n15);
                tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n15);
                tessellator.addVertexWithUV(n23, y - 0.005, n21, n12, n16);
                tessellator.addVertexWithUV(n24, y - 0.005, n21, n13, n16);
                tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n15);
            }
        }
        return true;
    }

    @Redirect(
        method = "renderCrossedSquares(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/Icon;"))
    private Icon redirectGetBlockIconFromSideAndMetadata(RenderBlocks instance, Block block, int side, int meta,
        Block specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockDoublePlant(Lnet/minecraft/block/BlockDoublePlant;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockDoublePlant;func_149888_a(ZI)Lnet/minecraft/util/Icon;"))
    private Icon modifyRenderBlockDoublePlant(BlockDoublePlant block, boolean top, int meta,
        BlockDoublePlant specializedBlock, int x, int y, int z) {
        return CTMUtils.getBlockIcon(
            block.func_149888_a(top, meta),
            (RenderBlocks) (Object) this,
            block,
            this.blockAccess,
            x,
            y,
            z,
            -1);
    }

    @Redirect(
        method = { "renderStandardBlockWithColorMultiplier(Lnet/minecraft/block/Block;IIIFFF)Z",
            "renderStandardBlockWithAmbientOcclusionPartial(Lnet/minecraft/block/Block;IIIFFF)Z",
            "renderStandardBlockWithAmbientOcclusion(Lnet/minecraft/block/Block;IIIFFF)Z" },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockGrass;getIconSideOverlay()Lnet/minecraft/util/Icon;",
            ordinal = 0))
    private Icon redirectGrassSideOverLay1(Block block, int x, int y, int z, float red, float green, float blue) {
        return CTMUtils.getBlockIcon(
            BlockGrass.getIconSideOverlay(),
            (RenderBlocks) (Object) this,
            block,
            this.blockAccess,
            x,
            y,
            z,
            2);
    }

    @Redirect(
        method = { "renderStandardBlockWithColorMultiplier(Lnet/minecraft/block/Block;IIIFFF)Z",
            "renderStandardBlockWithAmbientOcclusionPartial(Lnet/minecraft/block/Block;IIIFFF)Z",
            "renderStandardBlockWithAmbientOcclusion(Lnet/minecraft/block/Block;IIIFFF)Z" },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockGrass;getIconSideOverlay()Lnet/minecraft/util/Icon;",
            ordinal = 1))
    private Icon redirectGrassSideOverLay2(Block block, int x, int y, int z, float red, float green, float blue) {
        return CTMUtils.getBlockIcon(
            BlockGrass.getIconSideOverlay(),
            (RenderBlocks) (Object) this,
            block,
            this.blockAccess,
            x,
            y,
            z,
            3);
    }

    @Redirect(
        method = { "renderStandardBlockWithColorMultiplier(Lnet/minecraft/block/Block;IIIFFF)Z",
            "renderStandardBlockWithAmbientOcclusionPartial(Lnet/minecraft/block/Block;IIIFFF)Z",
            "renderStandardBlockWithAmbientOcclusion(Lnet/minecraft/block/Block;IIIFFF)Z" },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockGrass;getIconSideOverlay()Lnet/minecraft/util/Icon;",
            ordinal = 2))
    private Icon redirectGrassSideOverLay3(Block block, int x, int y, int z, float red, float green, float blue) {
        return CTMUtils.getBlockIcon(
            BlockGrass.getIconSideOverlay(),
            (RenderBlocks) (Object) this,
            block,
            this.blockAccess,
            x,
            y,
            z,
            4);
    }

    @Redirect(
        method = { "renderStandardBlockWithColorMultiplier(Lnet/minecraft/block/Block;IIIFFF)Z",
            "renderStandardBlockWithAmbientOcclusionPartial(Lnet/minecraft/block/Block;IIIFFF)Z",
            "renderStandardBlockWithAmbientOcclusion(Lnet/minecraft/block/Block;IIIFFF)Z" },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockGrass;getIconSideOverlay()Lnet/minecraft/util/Icon;",
            ordinal = 3))
    private Icon redirectGrassSideOverLay4(Block block, int x, int y, int z, float red, float green, float blue) {
        return CTMUtils.getBlockIcon(
            BlockGrass.getIconSideOverlay(),
            (RenderBlocks) (Object) this,
            block,
            this.blockAccess,
            x,
            y,
            z,
            5);
    }

    @Redirect(
        method = "renderBlockHopperMetadata(Lnet/minecraft/block/BlockHopper;IIIIZ)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/Icon;"))
    private Icon modifyRenderBlockHopperMetadata(RenderBlocks instance, Block block, int side, int meta,
        BlockHopper specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "getBlockIcon(Lnet/minecraft/block/Block;Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/Icon;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getIconSafe(Lnet/minecraft/util/Icon;)Lnet/minecraft/util/Icon;"))
    private Icon modifyGetBlockIcon(RenderBlocks instance, Icon texture, Block block, IBlockAccess blockAccess, int x,
        int y, int z, int side) {
        return CTMUtils.getBlockIcon(
            this.getIconSafe(block.getIcon(blockAccess, x, y, z, side)),
            (RenderBlocks) (Object) this,
            block,
            blockAccess,
            x,
            y,
            z,
            side);
    }

    @Redirect(
        method = "getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/Icon;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getIconSafe(Lnet/minecraft/util/Icon;)Lnet/minecraft/util/Icon;"))
    private Icon modifyGetBlockIconFromSideAndMetadata(RenderBlocks instance, Icon texture, Block block, int side,
        int meta) {
        return CTMUtils
            .getBlockIcon(this.getIconSafe(block.getIcon(side, meta)), (RenderBlocks) (Object) this, block, side, meta);
    }

    @Redirect(
        method = "getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/Icon;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getIconSafe(Lnet/minecraft/util/Icon;)Lnet/minecraft/util/Icon;"))
    private Icon modifyGetBlockIconFromSide(RenderBlocks instance, Icon texture, Block block, int side) {
        return CTMUtils.getBlockIcon(
            this.getIconSafe(this.getIconSafe(block.getBlockTextureFromSide(side))),
            (RenderBlocks) (Object) this,
            block,
            side);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/Icon;",
            ordinal = 1))
    private Icon mcpatcherforge$redirectToGetBlockIcon(RenderBlocks instance, Block block, int side, int meta,
        Block specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/Icon;"))
    private Icon mcpatcherforge$redirectToGetBlockIcon(RenderBlocks instance, Block block, int side,
        Block specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }
}
