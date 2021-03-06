package ca.cmput301f13t03.adventure_datetime.view.treeView;

import android.content.res.Resources;
import android.graphics.*;
import android.graphics.Paint.Align;
import ca.cmput301f13t03.adventure_datetime.R;
import ca.cmput301f13t03.adventure_datetime.model.StoryFragment;

/**
 * Represents a story fragment on the tree view
 * @author Jesse
 *
 */
class FragmentNode extends Region
{
	private static final int MAX_TXT_FOR_FIRST_LINE = 18;
	private static final int MAX_TXT_FOR_SECOND_LINE = 14;

	private Bitmap m_currentBackground = null;
	private Bitmap m_backgroundImage = null;
	private Bitmap m_selectedBackgroundImage = null;
	
	private StoryFragment m_fragment = null;
	private String m_lineOne = null;
	private String m_lineTwo = null;

	private static Paint s_backgroundPaint = null;
	private static Paint s_textPaint = null;

	// I love this feature!
	static
	{
		s_backgroundPaint = new Paint();

		s_textPaint = new Paint();
		s_textPaint.setColor(Color.BLACK);
		s_textPaint.setTextAlign(Align.CENTER);
		s_textPaint.setTextSize(35);
	}

	public FragmentNode(StoryFragment fragment, Resources res)
	{
		super(0, 0, 0, 0); // width and height will be updated momentarily...
		this.m_fragment = fragment;
		m_backgroundImage = BitmapFactory.decodeResource(res, R.drawable.bk_fragment_node);
		m_selectedBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.bk_fragment_selected_node);
		m_currentBackground = m_backgroundImage;
		this.width = m_backgroundImage.getWidth();
		this.height = m_backgroundImage.getHeight();

		RefreshContents();
	}
	
	public void SetIsSelected(boolean isSelected)
	{
		m_currentBackground = isSelected ? m_selectedBackgroundImage : m_backgroundImage;
	}

	public void RefreshContents()
	{
		String storyTxt = m_fragment.getStoryText();

		if(storyTxt.length() > MAX_TXT_FOR_FIRST_LINE + MAX_TXT_FOR_SECOND_LINE)
		{
			m_lineOne = ChopString(storyTxt, MAX_TXT_FOR_FIRST_LINE, 0).trim();
			m_lineTwo = ChopString(storyTxt, MAX_TXT_FOR_SECOND_LINE, m_lineOne.length()).trim() + "...";
		}
		else if(storyTxt.length() > MAX_TXT_FOR_FIRST_LINE)
		{
			m_lineOne = ChopString(storyTxt, MAX_TXT_FOR_FIRST_LINE, 0).trim();
			m_lineTwo = storyTxt.substring(m_lineOne.length(), storyTxt.length()).trim();
		}
		else
		{
			m_lineOne = storyTxt;
			m_lineTwo = "";
		}
	}

	/**
	 * Helper function that finds an appropriate spot to cut the string
	 * as close to the end as possible without chopping a word up
	 */
	private String ChopString(String str, int length, int start)
	{
		String result = str.substring(start, length + start);
		int lastIndex = result.lastIndexOf(' ');

		if(lastIndex > 0)
		{
			return result.substring(0, lastIndex);
		}
		else
		{
			return result;
		}
	}

	public StoryFragment GetFragment()
	{
		return m_fragment;
	}

	public void Draw(Canvas surface, Camera camera)
	{
		camera.DrawLocal(surface, s_backgroundPaint, m_currentBackground, this.x, this.y);
		camera.DrawLocal(surface, s_textPaint, m_lineOne, this.x + this.width / 2, this.y + this.height * 1 / 3);
		camera.DrawLocal(surface, s_textPaint, m_lineTwo, this.x + this.width / 2, this.y + this.height * 2 / 3);
	}
}
