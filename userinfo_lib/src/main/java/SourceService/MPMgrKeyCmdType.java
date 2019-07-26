package SourceService;
public enum MPMgrKeyCmdType
{
	eType_Unknown(0),
	eType_Push(1),
	eType_Release(2),
	eType_ShortPush(3),
	eType_LongPush(4),
	eType_Repeat(5);
	private int value = 0;
	private MPMgrKeyCmdType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static MPMgrKeyCmdType valueOf(int v) {
		switch(v){
			case 0:return eType_Unknown;
			case 1:return eType_Push;
			case 2:return eType_Release;
			case 3:return eType_ShortPush;
			case 4:return eType_LongPush;
			case 5:return eType_Repeat;
			default:return null;
		}
	}

	public static class Ref {
		private MPMgrKeyCmdType v;
		public Ref(MPMgrKeyCmdType v){ this.v = v;}
		public void setValue(MPMgrKeyCmdType v) {this.v = v;}
		public MPMgrKeyCmdType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(MPMgrKeyCmdType v){return new Ref(v);}}

