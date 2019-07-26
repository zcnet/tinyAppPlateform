package MTunerService;
public enum EPresetType
{
	PRESETTYPE_UNKNOWN(0),
	PRESETTYPE_PRESETUP(1),
	PRESETTYPE_PRESETDOWN(2);
	private int value = 0;
	private EPresetType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static EPresetType valueOf(int v) {
		switch(v){
			case 0:return PRESETTYPE_UNKNOWN;
			case 1:return PRESETTYPE_PRESETUP;
			case 2:return PRESETTYPE_PRESETDOWN;
			default:return null;
		}
	}

	public static class Ref {
		private EPresetType v;
		public Ref(EPresetType v){ this.v = v;}
		public void setValue(EPresetType v) {this.v = v;}
		public EPresetType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(EPresetType v){return new Ref(v);}}

