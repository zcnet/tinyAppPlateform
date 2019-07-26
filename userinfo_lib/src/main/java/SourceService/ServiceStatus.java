package SourceService;
public enum ServiceStatus
{
	eServiceStatus_Unknown(0),
	eServiceStatus_Actived(1),
	eServiceStatus_Deactived(2);
	private int value = 0;
	private ServiceStatus(int value) {this.value = value;}
	public int value() {return this.value;}
	public static ServiceStatus valueOf(int v) {
		switch(v){
			case 0:return eServiceStatus_Unknown;
			case 1:return eServiceStatus_Actived;
			case 2:return eServiceStatus_Deactived;
			default:return null;
		}
	}

	public static class Ref {
		private ServiceStatus v;
		public Ref(ServiceStatus v){ this.v = v;}
		public void setValue(ServiceStatus v) {this.v = v;}
		public ServiceStatus getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(ServiceStatus v){return new Ref(v);}}

