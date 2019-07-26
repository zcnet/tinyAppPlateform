package SourceService;
public enum SrcStatus
{
	eSrcStatus_Unknown(0),
	eSrcStatus_Initialization(1),
	eSrcStatus_Registering(2),
	eSrcStatus_LoadingMedia(3),
	eSrcStatus_NoMedia(4),
	eSrcStatus_InvalidMedia(5),
	eSrcStatus_Ready(6),
	eSrcStatus_ReserveOn(7),
	eSrcStatus_Reserve(8),
	eSrcStatus_ReserveOff(9),
	eSrcStatus_Activating(10),
	eSrcStatus_Activated(11),
	eSrcStatus_Deactivating(12),
	eSrcStatus_Deregistering(13),
	eSrcStatus_Destroy(14);
	private int value = 0;
	private SrcStatus(int value) {this.value = value;}
	public int value() {return this.value;}
	public static SrcStatus valueOf(int v) {
		switch(v){
			case 0:return eSrcStatus_Unknown;
			case 1:return eSrcStatus_Initialization;
			case 2:return eSrcStatus_Registering;
			case 3:return eSrcStatus_LoadingMedia;
			case 4:return eSrcStatus_NoMedia;
			case 5:return eSrcStatus_InvalidMedia;
			case 6:return eSrcStatus_Ready;
			case 7:return eSrcStatus_ReserveOn;
			case 8:return eSrcStatus_Reserve;
			case 9:return eSrcStatus_ReserveOff;
			case 10:return eSrcStatus_Activating;
			case 11:return eSrcStatus_Activated;
			case 12:return eSrcStatus_Deactivating;
			case 13:return eSrcStatus_Deregistering;
			case 14:return eSrcStatus_Destroy;
			default:return null;
		}
	}

	public static class Ref {
		private SrcStatus v;
		public Ref(SrcStatus v){ this.v = v;}
		public void setValue(SrcStatus v) {this.v = v;}
		public SrcStatus getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(SrcStatus v){return new Ref(v);}}

