package MTunerService;
public enum EPresetCtrType
{
	PRESETCTLTYPE_UNKNOWN(0),
	PRESETCTLTYPE_ADD(1),
	PRESETCTLTYPE_DEL(2);
	private int value = 0;
	private EPresetCtrType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static EPresetCtrType valueOf(int v) {
		switch(v){
			case 0:return PRESETCTLTYPE_UNKNOWN;
			case 1:return PRESETCTLTYPE_ADD;
			case 2:return PRESETCTLTYPE_DEL;
			default:return null;
		}
	}

	public static class Ref {
		private EPresetCtrType v;
		public Ref(EPresetCtrType v){ this.v = v;}
		public void setValue(EPresetCtrType v) {this.v = v;}
		public EPresetCtrType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(EPresetCtrType v){return new Ref(v);}}

