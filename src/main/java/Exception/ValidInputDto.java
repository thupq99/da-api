package Exception;

import com.globits.da.dto.HuyenDto;
import com.globits.da.dto.TinhDto;
import com.globits.da.dto.VanbangDto;
import com.globits.da.dto.XaDto;

public class ValidInputDto {

	public String ValidvanBang(VanbangDto dto) {
		if (dto.getCode().length() > 10 || dto.getCode().length() < 6)
			return "Code dai toi da 10, toi thieu 6 ky tu";
		if (dto.getCode() == null || dto.getCode().length() == 0)
			return "code ko dc de trong";
		if (dto.getName() == null || dto.getName().length() == 0)
			return "name ko dc de trong";
		return "success";
	}

	public String ValidTinh(TinhDto dto) {
		if (dto.getCode().length() > 10 || dto.getCode().length() < 6)
			return "Code dai toi da 10, toi thieu 6 ky tu";
		if (dto.getCode() == null || dto.getCode().length() == 0)
			return "code ko dc de trong";
		if (dto.getName() == null || dto.getName().length() == 0)
			return "name ko dc de trong";
		return "success";
	}

	public String ValidHuyen(HuyenDto dto) {
		if (dto.getCode().length() > 10 || dto.getCode().length() < 6)
			return "Code dai toi da 10, toi thieu 6 ky tu";
		if (dto.getCode() == null || dto.getCode().length() == 0)
			return "code ko dc de trong";
		if (dto.getName() == null || dto.getName().length() == 0)
			return "name ko dc de trong";
		return "success";
	}

	public String ValidXa(XaDto dto) {
		if (dto.getCode().length() > 10 || dto.getCode().length() < 6)
			return "Code dai toi da 10, toi thieu 6 ky tu";
		if (dto.getCode() == null || dto.getCode().length() == 0)
			return "code ko dc de trong";
		if (dto.getName() == null || dto.getName().length() == 0)
			return "name ko dc de trong";
		return "success";
	}
}
