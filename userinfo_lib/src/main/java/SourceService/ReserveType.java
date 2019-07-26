package SourceService;
public enum ReserveType
{
	ReserveType_Unknown(0),
	ReserveType_ON(1),
	ReserveType_OFF(2);
	private int value = 0;
	private ReserveType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static ReserveType valueOf(int v) {
		switch(v){
			case 0:return ReserveType_Unknown;
			case 1:return ReserveType_ON;
			case 2:return ReserveType_OFF;
			default:return null;
		}
	}

	public static class Ref {
		private ReserveType v;
		public Ref(ReserveType v){ this.v = v;}
		public void setValue(ReserveType v) {this.v = v;}
		public ReserveType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(ReserveType v){return new Ref(v);}}

