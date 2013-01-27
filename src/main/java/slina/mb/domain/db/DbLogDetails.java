package slina.mb.domain.db;

public interface DbLogDetails {

	public abstract Long getLogDetailLineNumber();

	public abstract String getLogLineValue();

	public abstract DbLogEventImpl getLogEvent();

}