package de.utopiamc.framework.worker.economy.packet;

public class EconomyHoldingsUpdatePacket extends EconomyPacket {

    private static final String PACKET_ID = "economy.holdings.update";

    private final Double previousValue;
    private final Double newValue;

    private final EconomyHoldingsUpdateCause cause;

    protected EconomyHoldingsUpdatePacket(EconomyHoldingsUpdatePacketBuilder<?, ?> b) {
        super(b);
        this.previousValue = b.previousValue;
        this.newValue = b.newValue;
        this.cause = b.cause;
    }

    public static EconomyHoldingsUpdatePacketBuilder<?, ?> builder() {
        return new EconomyHoldingsUpdatePacketBuilderImpl();
    }

    private static final class EconomyHoldingsUpdatePacketBuilderImpl extends EconomyHoldingsUpdatePacketBuilder<EconomyHoldingsUpdatePacket, EconomyHoldingsUpdatePacketBuilderImpl> {
        private EconomyHoldingsUpdatePacketBuilderImpl() {
        }

        protected EconomyHoldingsUpdatePacketBuilderImpl self() {
            return this;
        }

        public EconomyHoldingsUpdatePacket build() {
            packetId(PACKET_ID);
            return new EconomyHoldingsUpdatePacket(this);
        }
    }

    public static abstract class EconomyHoldingsUpdatePacketBuilder<C extends EconomyHoldingsUpdatePacket, B extends EconomyHoldingsUpdatePacketBuilder<C, B>> extends EconomyPacketBuilder<C, B> {
        private Double previousValue;
        private Double newValue;
        private EconomyHoldingsUpdateCause cause;

        public B previousValue(Double previousValue) {
            this.previousValue = previousValue;
            return self();
        }

        public B newValue(Double newValue) {
            this.newValue = newValue;
            return self();
        }

        public B cause(EconomyHoldingsUpdateCause cause) {
            this.cause = cause;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "EconomyHoldingsUpdatePacket.EconomyHoldingsUpdatePacketBuilder(super=" + super.toString() + ", previousValue=" + this.previousValue + ", newValue=" + this.newValue + ", cause=" + this.cause + ")";
        }
    }
}
