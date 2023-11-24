package net.isotopia.mod.cap;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IPlayerRad extends INBTSerializable<CompoundNBT> {
    void tick();

    void update();

    double momentDose();

    double getDose();
    /** 1.17: IStorage interface removed with no replacement. Move these methods to the provider that implements {@linkplain ICapabilitySerializable}*/
    @Deprecated
    public class Storage implements Capability.IStorage<IPlayerRad> {

        @Override
        public INBT writeNBT(Capability<IPlayerRad> capability, IPlayerRad instance, Direction side) {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<IPlayerRad> capability, IPlayerRad instance, Direction side, INBT nbt) {
            if (nbt instanceof CompoundNBT)
                instance.deserializeNBT((CompoundNBT) nbt);
        }
    }

    class Provider implements ICapabilitySerializable<CompoundNBT> {

        IPlayerRad data;

        public Provider(IPlayerRad data) {
            this.data = data;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == Capabilities.PLAYER_RAD ? (LazyOptional<T>) LazyOptional.of(() -> data) : LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return data.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            data.deserializeNBT(nbt);
        }

    }
}
