package com.example.mixin;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public class MixinModelBiped {

    @Shadow public ModelRenderer bipedRightArm;
    @Shadow public ModelRenderer bipedLeftArm;
    @Shadow public int heldItemRight;
    @Shadow public int heldItemLeft;
    @Shadow public boolean aimedBow;

    @Inject(method = "setRotationAngles", at = @At("TAIL"))
    private void applyAlphaWalk(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn, CallbackInfo ci) {
        if (this.heldItemRight == 0 && this.heldItemLeft == 0 && !this.aimedBow) {
            this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount;
            this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount;

            this.bipedRightArm.rotateAngleZ = (MathHelper.cos(limbSwing * 0.2312F) + 1.0F) * 1.0F * limbSwingAmount;
            this.bipedLeftArm.rotateAngleZ = (MathHelper.cos(limbSwing * 0.2812F) - 1.0F) * 1.0F * limbSwingAmount;
        }
    }
}