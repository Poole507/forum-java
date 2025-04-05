package com.smartsignlanguage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartsignlanguage.domain.entity.Attachments;
import com.smartsignlanguage.mapper.AttachmentsMapper;
import com.smartsignlanguage.service.AttachmentsService;
import org.springframework.stereotype.Service;

/**
 * 附件信息表(Attachments)表服务实现类
 *
 * @author makejava
 * @since 2025-04-02 22:17:28
 */
@Service("attachmentsService")
public class AttachmentsServiceImpl extends ServiceImpl<AttachmentsMapper, Attachments> implements AttachmentsService {

}
