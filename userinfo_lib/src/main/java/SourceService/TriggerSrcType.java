package SourceService;
public enum TriggerSrcType
{
	TriggeredByUnknown(0),
	TriggeredByFaceplate(1),
	TriggeredBySWC(2),
	TriggeredByRemoteCtrl(3);
	private int value = 0;
	private TriggerSrcType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static TriggerSrcType valueOf(int v) {
		switch(v){
			case 0:return TriggeredByUnknown;
			case 1:return TriggeredByFaceplate;
			case 2:return TriggeredBySWC;
			case 3:return TriggeredByRemoteCtrl;
			default:return null;
		}
	}

	public static class Ref {
		private TriggerSrcType v;
		public Ref(TriggerSrcType v){ this.v = v;}
		public void setValue(TriggerSrcType v) {this.v = v;}
		public TriggerSrcType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(TriggerSrcType v){return new Ref(v);}}

