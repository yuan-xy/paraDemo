package ap;

import java.util.List;

public interface IDirectedGraphElement {
	public List<IDirectedGraphElement> getOutputList();

	public List<IDirectedGraphElement> getInputList();

	public void setVisitStatus(boolean isVisited);

	public boolean getVisitStatus();

	public void changeEdgeDirect();
}
